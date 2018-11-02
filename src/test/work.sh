#!/usr/bin/env bash

echo --------------------------------------------
curl -o - https://hello-endpoints-207818.appspot.com/monopoly/v1/player/1
echo --------------------------------------------
curl -o - https://hello-endpoints-207818.appspot.com/monopoly/v1/player/10

echo --------------------------------------------
curl \
    --header "Content-Type: application/json" \
    --request PUT \
    --data '{"emailAddress":"test1@test.edu"}' \
    https://hello-endpoints-207818.appspot.com/monopoly/v1/player/7

echo --------------------------------------------
curl \
    --header "Content-Type: application/json" \
    --request POST \
    --data '{"id":"100", "emailAddress":"test2@test.edu", "name":"meetoo"}' \
    https://hello-endpoints-207818.appspot.com/monopoly/v1/player

echo --------------------------------------------
curl \
    --request DELETE \
    https://hello-endpoints-207818.appspot.com/monopoly/v1/player/7
echo --------------------------------------------
curl \
    --request DELETE \
    https://hello-endpoints-207818.appspot.com/monopoly/v1/player/8
