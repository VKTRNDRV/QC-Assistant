# QC-Assistant

## Overview

The 'QC Assistant' application is a tool for assisting the task of performing Quality Control (QC) on configured orders that are at stage 'QC'. 
It automatically generates and displays to the user a selection of 'notes' on details/tasks that need to be verified/performed as per the business logic relevant to the order provided. 
This saves the user time, ensures any special cases covered by the application's logic are not missed/overlooked, and allows them to focus more on troubleshooting technical issues, _ad hoc_ cases and other points not covered by the 'QC Assistant'.

## Functionalities

### Entities:

- Adding/Editing/Viewing Destinations
- Adding/Editing/Viewing Languages

For each of the three clients (Medidata, IQVIA, Medable):
- Adding/Editing/Viewing Sponsors
- Adding/Editing/Viewing Studies
- Adding/Editing/Viewing Apps
- Adding/Editing/Viewing Tags

Entities' fields contain relevant information, which is utilized in the note generation business logic.

### Generating Notes:

For each of the three clients (Medidata, IQVIA, Medable):

**Step 1 - Order Parsing:**

The raw, copy-pasted text of an order is parsed and the order's details are detected:
- The Type of the order (PROD/UAT)
- The Destination
- Any requested Languages
- The Devices (iOS/Android/Windows/Medical)
- The SIMs
- Any Accessories included
- Any Documents requested
- The Study (or 'Unknown' Study if no match found)
- The Sponsor (or 'Unknown' Sponsor if no match found)

**Step 2 - Note Generation**

A specific set of notes is generated as per the relevant business logic considering the provided order's specifics.
Firstly, the order's details are evaluated and notes are added as per hardcoded business logic in the NoteGeneration classes.
Secondly, each of the respective client's Tag entities is evaluated on whether all of it's preconditions are met for the current order.
In case a tag is applicable, a note with the corressponding text, type and severity is created and added as per the tag's fields.

For ease of use, the notes are divided into several sections: 
- Shell check notes  
- Documentation and the devices' labels notes
- iOS devices' notes (if order contains iOS devices)
- Android devices' notes (if order contains Android devices)
- Windows devices' notes (if order contains Windows devices)

![Note_Generation_Diagram](https://lh3.googleusercontent.com/drive-viewer/AK7aPaBaaoHtiKo_WpOx83_S55JY5xqC5XIHOX8Q1ztQtn96qMxuZnMrvjoLxyB5NAA5th-4UTNmm9jofgGwfkVAtzoggBYLHQ=w1920-h963)

### Other

- The notes display pages' provide fuctionality to check all Devices' and SIMs' serials for any mismatches, duplicates, or label typos
- User role management from inside the application, with relevant permissions for each role
