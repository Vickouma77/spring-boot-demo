#!/bin/bash

# Function to list subdirectories
list_subdirectories() {
    # shellcheck disable=SC2155
    local current_dir=$(pwd)

    echo "Subdirectories in $current_dir:"
    # shellcheck disable=SC2005
    echo "$(printf '%.0s-' {1..40})"

    # Use find to list directories, excluding hidden ones
    find . -maxdepth 1 -type d ! -path '*/\.*' ! -path '.' | while read -r dir; do
        echo "- ${dir#./}"
    done
}

list_subdirectories