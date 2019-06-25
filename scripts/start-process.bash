#!/usr/bin/env bash
user=demo
password=demo

echo `curl -v  \
           -H "Content-Type: application/json" \
           -d {"businessKey" : "000"} \
           -X POST http://localhost:8090/api/engine/engine/default/process-definition/test-process-0/start`
