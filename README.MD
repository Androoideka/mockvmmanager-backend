# VM Manager

This repository contains the source code of the backend for the VM manager project, built with Java and Spring Boot. The frontend which is packaged together with the backend in the final build is built with TypeScript and Angular.

This project simulates managing tenancies and virtual machines on a cloud provider's website. Depending on allocated permissions, users can create, list, delete and modify users and create, start, stop, restart, delete and search machines. Operations on a machine can be queued for execution at regular intervals using a cron job, or executed immediately. Machine state is communicated immediately using WebSockets so the user is always aware of the state of their machines.

## Deployment

The frontend is packaged automatically with the backend and an in-memory database through both the GitHub Actions workflow and the Dockerfile. 

The final result is a zip archive with a self-contained build that can be run on Windows. A jar is included for testing on Linux, but requires a JDK to be installed.

After running the app, the home page can be accessed at localhost:8080 in your browser. To log in initially, use the credentials of the hardcoded superuser.
Username: agasic218rn
Password: tianming

If you wish to produce the build locally instead of relying on GitHub Actions, use this command:

```
docker build --output type=local,dest=out --build-context frontend=https://github.com/Androoideka/vm-manager-frontend.git .
```