# Use Poetry to manage dependencies within the active Conda environment.
# Assumes you're already inside a Conda environment with Python installed.

.PHONY: install install-dev lock show clean

install:
	@echo "Installing production dependencies with Poetry into the current environment..."
	poetry config virtualenvs.create false
	poetry export -f requirements.txt --output requirements.txt --without-hashes
	pip install -r requirements.txt

install-dev:
	@echo "Installing all dependencies (including development tools) with Poetry into the current environment..."
	poetry install --no-root

lock:
	@echo "Updating lock file..."
	poetry lock

show:
	@echo "Current Poetry environment (showing installed packages):"
	poetry show

clean:
	@echo "Cleaning cache and build artifacts..."
	rm -rf build dist *.egg-info
