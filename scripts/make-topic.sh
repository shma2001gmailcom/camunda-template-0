#!/usr/bin/env bash

#!/usr/bin/env bash

#!/bin/sh
export $(grep -v '^#' ./environment.properties | xargs)
cd ${kafka}/bin
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic $1
