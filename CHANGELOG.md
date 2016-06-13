# TogglAPI Release Notes

## Date: 
06-Jun-2016

## Version: 
v0.1.0-alpha

## Supports Toggl API versions:  
v8

## Overview
This release provides limited functionality but it's being used currently to move time-keeping data
for project development from the online Toggl service to a local relational database (MySQL). This 
alpha release has not been tested thoroughly and is not recommended for production use at this time.
 
## Supported Mule Runtime Versions: 
3.7.2

## New Features and Functionality
- Pull JSON time data for a Toggl project
- Bulk tag time data for a Toggl project
   
## Closed Issues in this release
https://github.com/ciwise/toggl-api-connector/issues/1
https://github.com/ciwise/toggl-api-connector/issues/2
https://github.com/ciwise/toggl-api-connector/issues/3
https://github.com/ciwise/toggl-api-connector/issues/4
https://github.com/ciwise/toggl-api-connector/issues/5
https://github.com/ciwise/toggl-api-connector/issues/6
https://github.com/ciwise/toggl-api-connector/issues/7 
https://github.com/ciwise/toggl-api-connector/issues/8

## Known Issues in this release
A little mysterious because you need integer ids when you configure the connector. Obtaining these ids
takes a little investigation of the URLs on the Toggl site.
