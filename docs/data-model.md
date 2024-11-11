---
title: Data Model
description: "UML class diagram, entity-relationship diagram, and DDL."
order: 20
---

{% include ddc-abbreviations.md %}

## Page contents
{:.no_toc:}

- ToC
{:toc}

## UML class diagram

[![UML Class Diagram](img/FiremanUML.svg)](pdf/FiremanUML.pdf)

---

## ERD class diagram

[![Entity Relationship Diagram (ERD)](img/fireman-server-erd.svg)](pdf/fireman-server-erd.pdf)

---

## Entity Classes

- [Game](https://github.com/ddc-java-19/fireman/blob/main/server/src/main/java/edu/cnm/deepdive/fireman/model/entity/Game.java)

- [Move](https://github.com/ddc-java-19/fireman/blob/main/server/src/main/java/edu/cnm/deepdive/fireman/model/entity/Move.java)

- [Plot](https://github.com/ddc-java-19/fireman/blob/main/server/src/main/java/edu/cnm/deepdive/fireman/model/entity/Plot.java)

- [User](https://github.com/ddc-java-19/fireman/blob/main/server/src/main/java/edu/cnm/deepdive/fireman/model/entity/User.java)

---

## Data Definition Language (DDL)

{% include linked-file.md type="sqlite" file="sql/ddl-server.sql" %}