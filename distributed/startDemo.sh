#!/usr/bin/env bash

java  -jar payara-micro-5.191.jar --port 8080 servicea/target/servicea.war &
java  -jar payara-micro-5.191.jar --port 8081 serviceb/target/serviceb.war &
java  -jar payara-micro-5.191.jar --port 8082 servicec/target/servicec.war &

