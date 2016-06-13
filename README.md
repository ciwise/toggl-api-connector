# TogglAPI Anypoint Connector

[![Build Status](https://api.travis-ci.org/ciwise/toggl-api-connector.png?branch=master)](https://travis-ci.org/ciwise/toggl-api-connector)

The TogglAPI Anypoint Connector provides an authenticated connection via HTTPS to the Toggl online timekeeping service API. This Mule
connector can be used in a Mule flow to communicate with the Toggl service in a programmatic and process manner outside of the website.
Use of the connector does not require direct user interaction. The Mule flows are kicked off using an HTTP connector and a REST API.
 
## Functionality:

- Pull JSON timekeeping data from Toggl by project (New! v0.1.0-alpha release)
- Bulk tag time entries e.g. after loading them into a database (New! v0.1.0-alpha release)
- Pull tailored timekeeping data for invoicing clients (future release)
- Pull tailored timekeeping data for consultant timesheets (future release) 

This connector will be used for the CI Wise Consultant Suite.

# Mule supported versions
Mule 3.7.2

# CI Wise Consultant Suite supported versions
Consultant Suite v1.0.0 and above.

# Use with other connectors
HTTP, JSON-Object, Database, Logging

# Installation 
For the alpha release, you can clone the repository, build and install the connector local for use in Anypoint Studio at your own risk.
A relationship with MuleSoft is pending and you can only build and install local at this time. On the beta release, the connector will
have been tested thoroughly and deemed acceptable for public-use.

Upon the release of v1.0.0 and later, you will be able to download the connector from the update site in Anypoint Studio. Open Anypoint 
Studio, go to Help → Install New Software and select Anypoint Connectors Update Site where you’ll find all avaliable connectors.

#Usage
Usage documentation can be found here: https://github.com/ciwise/toggl-api-connector/wiki

# Reporting Issues
We use GitHub:Issues for tracking issues with this connector. You can report new issues at this link http://github.com/ciwise/toggl-api-connector/issues.
