.PHONY: all install build test run clean docker-build docker-up lint

PYTHON ?= python
PIP ?= pip
NPM ?= npm
MVN ?= mvn

all: install build test

install:
	$(PIP) install -r requirements.txt
	$(NPM) install

build:
	$(MVN) clean compile -DskipTests
	$(NPM) run build

test:
	pytest tests/ --cov=. --cov-report=term-missing

run:
	$(PYTHON) main.py

start:
	$(NPM) start

lint:
	flake8 . --count --select=E9,F63,F7,F82 --show-source --statistics || true

clean:
	rm -rf build dist *.egg-info .pytest_cache .coverage htmlcov target node_modules

docker-build:
	docker build -t fraud-pipeline:latest .

docker-up:
	docker-compose up -d
