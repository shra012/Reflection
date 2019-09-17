#!/bin/bash -ex

mkdir -p .m2/repository

mvn versions:set -DnewVersion=1.0-SNAPSHOT -Dmaven.repo.local=.m2/repository

mvn package -Dmaven.repo.local=.m2/repository

cp target/*.jar ../artifacts