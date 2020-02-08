# to-do-app

## Overview
To-do-app is a RESTful API for managing to-do lists.

## Technical Details
- To-do List contains the following entities: TodoItem, TodoList, Category.
- A TodoItem contains information for: title, description, if the task is completed, date of creation, date of last update.
- A TodoList contains information for: name, categories, date of creation, date of last update.
- A category contains information for: name and related TodoLists.

## Request-response table

| Type | URL | Parameters | Response |
| ---- | --- | ---------- | -------- |
| GET | /resources | n/a | 200 OK - all resourcses retrieved |
| GET | /resources/{id} | {id} - identifier of specific resource | 200 OK - resource with specified id <br />404 Not found - if resource with the given {id} does not exist |
| POST | /resources | JSON object for the resource to be created | 201 Created - the newly created resource <br />409 Conflict - if a resource with a specified unique field already exists |
| PUT | /resources/{id} | {id} - identifier of the resource to be updated Request body - JSON object to be updated | 200 OK - the successfully updated resource <br />404 Not found - if resource with the given {id} does not exist <br />409 Conflict - if a resource with a specified unique field already exists |
| PATCH | /resources/{id} | {id} - identifier of the resource to be updated Request body - partial JSON object with the fields to be updated | 200 OK - the successfully updated resource <br />
404 Not found - if resource with the given {id} does not exist <br />
409 Conflict - if a resource with a specified unique field already exists |






