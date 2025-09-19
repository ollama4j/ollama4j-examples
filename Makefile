dev:
	@echo "Setting up dev environment..."
	@command -v pre-commit >/dev/null 2>&1 || { echo "Error: pre-commit is not installed. Please install it first."; exit 1; }
	@command -v docker >/dev/null 2>&1 || { echo "Error: docker is not installed. Please install it first."; exit 1; }
	@pre-commit install
	@pre-commit autoupdate
	@pre-commit install --install-hooks

check-formatting:
	@echo "\033[0;34mChecking code formatting...\033[0m"
	@mvn spotless:check

apply-formatting:
	@echo "\033[0;32mApplying code formatting...\033[0m"
	@mvn spotless:apply
	# pre-commit run --all-files

run: apply-formatting
	@echo "\033[0;34mRunning batch...\033[0m"
	@mvn clean install exec:java

build: apply-formatting
	@echo "\033[0;34mBuilding...\033[0m"
	@mvn clean install