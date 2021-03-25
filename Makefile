compile:
	sbt --client compile

test:
	sbt --client test

uberjar:
	sbt --client clean
	sbt --client assembly

run:
	sbt --client run

shutdown-sbt:
	sbt --client shutdown

docker-build:
	docker build -t assignment-server .

docker-run:
	docker run \
	--rm \
	-it \
	-p 8080:8080 \
	assignment-server

docker-all: docker-build docker-run