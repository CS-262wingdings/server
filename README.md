This code implements a Google Cloud AppEngine application that
integrates a Cloud SQL (Postgresql) database with an Endpoints REST
API. It is deployed to a Google Cloud project that has resources for
AppEngine and Cloud SQL. 

The domain of application is the Monopoly database. Only the players
API endpoints are implemented. See the code for detailed descriptions
of the functionality.

### Deployment Instructions

To install and configure the cloud service, do the following.

1. Create a new Google App Engine Project according to these instructions:
[Quickstart for Java 8 for App Engine Standard Environment](https://cloud.google.com/appengine/docs/standard/java/quickstart).
Notes for each step of the quickstart tutorial:
    - *Before you begin*
        1. Step 1
            - Use a unique name for your team project,
                e.g.: `calvincs262-fall2018-TEAMNAME`
            - Set the billing on your new project to an account
                number we provide to one of the members of your team.
        2. Step 2
            - Do only step 2.c.
                - Use one team member's Google account.
        3. Create an AppEngine App using the GCloud console.
            - Go to the project navigation, "App Engine"-"Dashboard".
            - Create a new application.
                - We suggest using this region: `us-east4`.
            - Skip steps 2.a-b & 2.d-f (unless you are configuring your own machine).
    - *Downloading the Hello World app*
        - Use the Lab 9 application cloned from the CS 262 code repo instead.
        - This will be your team project server, so copy this into a
            separate server repo on your team's GitHub organization.
    - *Running Hello World on your local machine*
        - Skip this step; there are bugs in the GCloud SQL support libraries
            that make local hosting unreliable. We'll deploy and run the system
            in the cloud.
    - *Deploying and running Hello World on App Engine*
        - Skip this step; we'll deploy the full app later.

2. Create a new CloudSQL instance for your new application according to
these instructions:
[Using Cloud SQL for PostgreSQL](https://cloud.google.com/appengine/docs/standard/java/cloud-sql/using-cloud-sql-postgres)
(cf. Google&rsquo;s [sample code repo](https://github.com/GoogleCloudPlatform/java-docs-samples/tree/master/appengine-java8/cloudsql-postgres)).
Note the following for each section of the tutorial:
    - *Before you begin*
        - Skip this step; you&rsquo;ve already done it.
    - *Setting up the Cloud SQL instance*
        1. Step 1 &mdash; Do as specified on the sub-page.
            - We suggest using the GCloud console for this.
            - Specify a PostgreSQL Development instance.
            - Use the "Development" engine configuration
                (the cheapest one).
            - Set an instance ID,
                e.g.: `calvincs262-fall2018-TEAMNAME-db`,
                and default user password. Remember these.
            - We suggest using this region: `us-east4`
                and this zone: `us-east4-b`.
        2. Steps 2&ndash;3 &mdash; You can skip these; you've just done them.
        3. Step 4 &mdash; Do this one as a sanity check.
        4. Step 5 &mdash; Skip this; it's done automatically.
        5. Step 6 &mdash: Edit the `pom.xml` as specified here,
            replacing the `LAB09-YOUR-*` entries as appropriate.
        6. Steps 7&ndash;8 &mdash; Skip these;
            this is already included in the lab 9 code.
    - *Granting access to App Engine*
        - Skip this step; your CloudSQL is built into the same application.
    - *Code sample overview*
        - Skip this step; review the Lab 9 sample code instead if desired.
    - *Testing in your development environment*
        - Skip this step; there is a
    [bug](https://stackoverflow.com/questions/50705839/cloudsql-eclipse-java-standard-gae-java-lang-unsatisfiedlinkerror)
    in the Cloud SDK socket factory that prevents it from working locally.
    The work around is to do code development in IDEA and Google Cloud
    project builds and deploys using the Google Cloud SDK shell. Note that 
    each re-deploy creates a backup version of the system that you should delete
    to save money.
    - *Deploying your app*
        - Skip this step as well; we'll deploy the full app later.
    - Finally, load the sample monopoly database as a test case.
        - On the SQL dashboard overview page, choose "Connect using Cloud Shell".
        - Login to your new PostgreSQL database instance.
        - Paste the `monopoly.sql` script from lab 7 into the commandline.
        - This should create the monopoly DB for testing.
            Try some SELECT commands to verify that it's there.
        - Eventually, you'll delete these tables and replace them with your
            team project database tables, but we suggest that you get the
            Monopoly service running first as a baseline.

3. Create a new CloudEndpoints API for your new application according to
these instructions:
[Getting Started with Endpoints Frameworks on App Engine](https://cloud.google.com/endpoints/docs/frameworks/java/get-started-frameworks-java)
(cf. Google&rsquo;s [sample code repo](https://github.com/GoogleCloudPlatform/java-docs-samples/tree/master/appengine-java8/endpoints-v2-backend)).
Note the following for each section of the tutorial:
    - *Before you begin* &
        *Installing and configuring required software* &
        *Getting the sample code*
        - Skip these; you&rsquo;ve already done them.
    - *Configuring Endpoints*
        - Do as written (but on the lab 9 sample code).
    - *Deploying the Endpoints configuration*
        - Do as written.
    - *Running the sample locally*
        - Skip this; it may not run because of the bug noted above.
    - *Deploying the API backend*
        - Do as written.
    - *Sending a request to the API*
        - Try this out (using the cURL commands given in the source code).
    - *Tracking API activity*
        - Do this as a sanity check.
    - *Creating a developer portal for the API*
        - Skip this.
    - *Clean up*
        - Do this only do this after the project is complete and the
            course grades submitted.

This new, baseline Monopoly DB should support the same features as the sample
service used for homework 2. When you've confirmed that it does, you can update
the code to support your project database.

The Google Cloud application/database incur charges when active, and we
have limited grant money to fund our project development. To cut costs,
you should delete legacy versions of the application, which are created
every time you (re)deploy the application. You can also manually shut the
system down when not in use by:

- Disabling the AppEngine instance (Dashboard-AppEngine-Settings-Disable)
- Stopping the SQL instance (Dashboard-SQL-Stop)

The system can be restarted by reversing these two actions.
Note that even a disabled project costs ~$1/day. Delete the cloud
project entirely when the course grades are submitted. You should,
of course, keep the source code in your repo so that you can rebuild
it later if needed.