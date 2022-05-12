# biokiste

## Usage

## Installation

### Web
Open the web frontend [here](https://biokiste.herokuapp.com/).

### Android
1. You have been invited to use the biokiste App: Go to step 2. If you have no invitation already, ask [Stefan Bollmann](mailto:stefan.bollmann@rwth-aachen.de) for an invitation. After receiving and accepting the invitation, go to step 2.
2. Search for biokiste in the Google Play Store and install it. 
3. Create a new account in the app.

### iOS
0. ``xcode-select --install`` for XCode command-line-tools.
1. Install the [Testflight](https://developer.apple.com/testflight/) app from the [App Store](https://apps.apple.com/us/app/testflight/id899247664).
2. If you have not yet got an invitation link for testing, ask [Stefan Bollmann](mailto:stefan.bollmann@rwth-aachen.de).
3. Install the biokiste App by using the Testflight App.
4. Create a new biokisten account in the app.

## Development

### Drawings
Most up to date at [Excalidraw](https://excalidraw.com/#json=s9IsmlYfdRogrS1eNLObL,3vjggi8Ya3kXuSB4HHUE4Q).
![Customer view](resources/biokiste_220512.png) and/or [clickable at figma](https://www.figma.com/file/Ysrx9lh0R2de59mFTg4TeT/Biokiste?node-id=7%3A21)


### Repository and Deployment
- Tickets, Code repository and Pipelines are hosted at [Github biokiste.](https://github.com/Boltzmann/biokiste)
- Web deployment of front- and backend at [heroku](https://dashboard.heroku.com/apps/biokiste/deploy/heroku-git).
- The database is deployed on [Apache Atlas](https://cloud.mongodb.com).
- For Hybrid native support for Android and iOS [Ionic React](https://ionicframework.com/docs/react) is used. The Apps are deployed in the testing environments of Google Play and Testflight for iOS App Store.

### Setup
For development [node.js](https://nodejs.org/en/), [Java OpenJDK >= 11](https://openjdk.java.net/projects/jdk/11/), and [Docker](https://www.docker.com/) are needed.

To get started (with Intellij Ultimate):
1. Clone the project from Github.
2. Load the Maven project in Intellij.
3. ``npm install -g @ionic/cli``.
4. In the frontend folder, use ``npm install``.

Happy Hacking!

### Code Ownership/Maintainer
[Stefan Bollmann](mailto:stefan.bollmann@rwth-aachen.de)

# Credits
This project was made in a bootcamp by [neuefische](https://www.neuefische.de). There are several colleagues that helped to start this "capstone" project by discussion, reviews or help in other ways. Thank you.

If you - however - helped and want to see your name here or do not, tell Stefan.

# Intellectual Property / Copyright
Copyright 2022, Tristan Koch and Stefan Bollmann. All Rights Reserved. 

Unauthorized copying of the code, logos, designs, and the idea is strictly prohibited proprietary and confidential. Written by [Stefan Bollmann](mailto:stefan.bollmann@rwth-aachen.de), May 2022.
