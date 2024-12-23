---
title: Overview
description: "Summary of in-progress or completed project."
order: 0
---

{% include ddc-abbreviations.md %}

## Page contents
{:.no_toc}

- ToC
{:toc}

## Summary

> In Fireman, two players compete with opposite goals: the Arsonist aims to ignite fires across the game board, while the other plays as the Fireman trying to extinguish those flames. The game board is divided into tiles, each representing a potential fire zone, which can either be set ablaze or kept safe depending on the players' actions. Each player has unique abilities and strategies designed to support their role, making each turn a strategic attempt to either grow or shrink the active fires on the board.

> The game continues until one player secures a majority of the tiles in their favor. If the Arsonist manages to spread flames over more tiles by the end of the game, they win. However, the fireman player can secure victory by containing or putting out every active fire, achieving complete control over the board. With plenty of room for strategy, Fireman combines fast-paced decisions with competitive, role-based play, challenging players to outmaneuver each other and gain control of the game board.

## Intended users and user stories

+ Person who likes playing games with friends:

    > As a person who likes to play strategy games while with some friends or in your free time, I want to play fireman in order to keep everyone busy while having fun and learn something about real fires.

+ Person who likes strategy games:

    > As a person who has always played board games with strategy involved, I enjoy a game that can be quick yet entertaining, I play fireman to keep my mind busy as well as allowing my brain to work through tough problems.

## Functionality

[//]: # (TODO List &#40;using a bullet list---or ordered list, if order is relevant&#41; the key functional aspects that will be provided by the app---i.e., tell us what the user will be able to do using the app. This should not simply be a re-statement of the [summary]&#40;#summary&#41;, but should instead provide a more specific articulation of the functionality and user experience. )

## Persistent data

* Game Board Layout
* Win/Loss Records Against Friends
* User Profiles and Authentication
* Game Settings
* Game Progress for Incomplete Games

## Device/external services

For our server side application we will be providing Google sign in so that players can be unique and keep trac of there past scores and wins, as well as being able to play a turn at any time with another player.

## Stretch goals and possible enhancements 

+ Terrain State

  > The plots would have different types of vegetation, affecting time burning.

  > The different vegetation types could also affect fire spread behavior.
  
+ Single Player

  > The user could choose to play by themselves as arsonist or fireman.
  
+ Rain/Lightning events

  > Rain would randomly cover a part of the board, putting out the fires in that section, aiding the fireman.
  
  > Lightning could randomly turn plots on fire, helping the arsonist.

+ Board size selection

  > We would like the users to select board size.

## Summary of the current project as of 11/22/2024

> As of now we have implemented basic gameplay through the server using Http GET, and POST requests. We are currently implementing some basic functions like wind direction being random. As well as adding a water bomb and how it will work. Also making the users moves persist so that fire and water are able to stay on the board for more than a single turn.

> Our projects documentation and diagrams are being kept up to date and are currently in good shape. Overall, our main focus is to get the game working how we had envisioned. The project is working and playable yet its missing some key components that will make it enjoyable.
