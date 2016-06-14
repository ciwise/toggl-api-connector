# TogglAPI Release Notes

## Date: 
14-Jun-2016

## Version: 
v0.2.0-alpha "Waffles Release"

## Supports Toggl API versions:  
v8

## Overview
This release provides limited functionality but it's being used currently to move time-keeping data
for project development from the online Toggl service to a local relational database (MySQL). This 
alpha release has not been tested thoroughly and is not recommended for production use at this time.
 
## Supported Mule Runtime Versions: 
3.7.2

## New Features and Functionality
- Use project name instead of searching for an id
- Workspace id is calculated from authenticated user
   
## Closed Issues in this release
- https://github.com/ciwise/toggl-api-connector/issues/9
- https://github.com/ciwise/toggl-api-connector/issues/10
- https://github.com/ciwise/toggl-api-connector/issues/11

## Known Issues in this release
Configuration of the user and userpass is redundant.

