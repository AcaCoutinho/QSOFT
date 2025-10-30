#!/bin/sh
# Minimal Maven wrapper delegating to installed Maven
if command -v mvn >/dev/null 2>&1; then
  exec mvn "$@"
else
  echo "Maven (mvn) binary not found on PATH." >&2
  exit 1
fi