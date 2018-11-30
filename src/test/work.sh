#!/usr/bin/env bash


# https://phonic-biplane-221307.appspot.com/question/v1/players

echo --------------------------------------------
curl -o - https://phonic-biplane-221307.appspot.com/question/v1/player/1
echo --------------------------------------------
curl -o - https://phonic-biplane-221307.appspot.com/question/v1/player/10

echo --------------------------------------------
curl \
    --header "Content-Type: application/json" \
    --request PUT \
    --data '{"emailAddress":"test1@test.edu"}' \
    https://phonic-biplane-221307.appspot.com/question/v1/player/7

echo --------------------------------------------
curl \
    --header "Content-Type: application/json" \
    --request POST \
    --data '{"contents":"This is another question"}' \
    https://phonic-biplane-221307.appspot.com/game/v1/question



    # --data '{"id":"100", "emailAddress":"test2@test.edu", "name":"meetoo"}' \
echo --------------------------------------------
curl \
    --request DELETE \
    https://phonic-biplane-221307.appspot.com/question/v1/player/7
echo --------------------------------------------
curl \
    --request DELETE \
    https://phonic-biplane-221307.appspot.com/question/v1/player/8
