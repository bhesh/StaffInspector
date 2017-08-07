#!/bin/sh

ssh bhession@sandbox.hession.local 'mongo company --eval "db.dropDatabase()"'
