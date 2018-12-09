# TapTrack App

This is the Android application for tapTrack, a simple nfc application that monitors which user has done which household chores.

## Overview

This app was created to settle a debate, who does more chores. 

1. Users download this app, and create an account
1. During the account creation process, you can join (or create) a group that other members can join (if they have the password). This is so all members of a household can see what others have done.
1. Attach Nfc tags to various places around the house, such as near the bin, near the washing up, on the hover, etc, these can be picked up from eBay for ~5-10p each
1. Every time a user completes a chore, they simply tap the nfc tag with their phone and their score is added to the backend
1. Users can view all the progress made by everyone within their group to bring around some healthy competition

## Backend

To track all of this, I created a backend (cunningly entitled tapTrackApi - https://github.com/jimbotops/tapTrackApi) that this app can talk to. This is a Node-Express server that connects to a MongoDB backend and stores all information about the user as well as the user database so they can log on. 

## Images
<img src="https://user-images.githubusercontent.com/9968106/49700192-55259e00-fbd3-11e8-8b19-5df816d01a77.png" alt="alt text" width="300" height="600">

Login page, which links to the MongoDB user store


<img src="https://user-images.githubusercontent.com/9968106/49700189-55259e00-fbd3-11e8-8833-7e3c3c7e3cef.png" alt="alt text" width="300" height="600">

List of chores with the count of who's done what and how many times. As can be seen here there are 2 users James and Kas who are battling it out
