from typing import Any
import httpx
from mcp.server.fastmcp import FastMCP

# Initialize FastMCP server
mcp = FastMCP("weather")

from typing import Any
import pandas as pd
import openmeteo_requests
import requests_cache
from retry_requests import retry

from mcp.server.fastmcp import FastMCP

# ================================================================
# Initialize MCP Server
# ================================================================
mcp = FastMCP("weather")

# ================================================================
# Setup Open-Meteo client
# ================================================================
cache_session = requests_cache.CachedSession(".cache", expire_after=3600)
retry_session = retry(cache_session, retries=5, backoff_factor=0.2)
openmeteo = openmeteo_requests.Client(session=retry_session)

# ================================================================
# Formatting Helpers
# ================================================================
def format_current(response) -> str:
    """Convert Open-Meteo current conditions into text."""
    current = response.Current()

    temp = current.Variables(0).Value()
    rain = current.Variables(1).Value()
    wind_speed = current.Variables(2).Value()
    wind_dir = current.Variables(3).Value()
    humidity = current.Variables(4).Value()

    return (
        f"Current Conditions:\n"
        f"• Temperature: {temp}°C\n"
        f"• Rain: {rain} mm\n"
        f"• Wind: {wind_speed} m/s from {wind_dir}°\n"
        f"• Relative Humidity: {humidity}%\n"
    )


def format_hourly(response) -> str:
    """Return next 5 hourly periods."""
    hourly = response.Hourly()

    times = pd.date_range(
        start=pd.to_datetime(hourly.Time(), unit="s", utc=True),
        end=pd.to_datetime(hourly.TimeEnd(), unit="s", utc=True),
        freq=pd.Timedelta(seconds=hourly.Interval()),
        inclusive="left"
    )

    temp = hourly.Variables(0).ValuesAsNumpy()
    rain = hourly.Variables(1).ValuesAsNumpy()
    visibility = hourly.Variables(2).ValuesAsNumpy()

    df = pd.DataFrame({
        "time": times,
        "temperature_2m": temp,
        "rain": rain,
        "visibility": visibility,
    })

    # Show next 5 hours
    out = ["Next Hours:"]
    for _, row in df.head(5).iterrows():
        out.append(
            f"\n{row['time']}:"
            f"\n• Temp: {row['temperature_2m']:.1f}°C"
            f"\n• Rain: {row['rain']} mm"
            f"\n• Visibility: {row['visibility']} m"
        )
    return "\n".join(out)


# ================================================================
# TOOLS
# ================================================================

@mcp.tool()
async def geocode_city(name: str, count: int = 1, language: str = "en") -> str:
    """
    Get geolocation (latitude, longitude, elevation, etc.) for a city name
    using the Open-Meteo Geocoding API.

    Args:
        name: City name (e.g., "Berlin")
        count: Maximum number of results to return (default: 5)
        language: Language for results (default: "en")
    """
    
    url = "https://geocoding-api.open-meteo.com/v1/search"
    params = {
        "name": name,
        "count": 1,
        "language": language,
        "format": "json"
    }

    async with httpx.AsyncClient(timeout=20.0) as client:
        try:
            resp = await client.get(url, params=params)
            resp.raise_for_status()
            data = resp.json()
        except Exception as e:
            return f"Unable to fetch geocode: {e}"

    # Handle empty or missing results
    if "results" not in data or not data["results"]:
        return f"No geocoding results found for '{name}'."

    # Format results into readable text
    lines = []
    for r in data["results"]:
        lines.append(
            f"""
Name: {r.get('name')}
Country: {r.get('country')}
Latitude: {r.get('latitude')}
Longitude: {r.get('longitude')}
Elevation: {r.get('elevation')} m
Timezone: {r.get('timezone')}
Population: {r.get('population')}
Feature Code: {r.get('feature_code')}
"""
        )

    return "\n---\n".join(lines)


@mcp.tool()
async def get_forecast(latitude: float, longitude: float) -> str:
    """
    Get weather forecast using Open-Meteo.

    Args:
        latitude: decimal latitude
        longitude: decimal longitude
    """
    url = "https://api.open-meteo.com/v1/forecast"

    params = {
        "latitude": latitude,
        "longitude": longitude,
        "hourly": ["temperature_2m", "rain", "visibility"],
        "current": [
            "temperature_2m",
            "rain",
            "wind_speed_10m",
            "wind_direction_10m",
            "relative_humidity_2m"
        ],
    }

    try:
        responses = openmeteo.weather_api(url, params=params)
        response = responses[0]
    except Exception as e:
        return f"Unable to fetch forecast: {e}"

    output = [
        f"Coordinates: {response.Latitude()}°N, {response.Longitude()}°E",
        f"Elevation: {response.Elevation()} m",
        "",
        format_current(response),
        "",
        format_hourly(response)
    ]

    return "\n".join(output)


# ================================================================
# MAIN ENTRYPOINT
# ================================================================
def main():
    mcp.run(transport="stdio")

if __name__ == "__main__":
    main()
