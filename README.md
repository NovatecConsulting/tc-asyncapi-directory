## AsyncAPI-UI

### Open Tasks
* **Upgrade to next-branch and standalone component instead of web component**: \
Needs further investigation: web component of next-branch did not apply css-tyles correctly; react
component of next-branch did not render externally fetched schema definitions!
* 



### Custom extensions
Nearly every object within an AsyncAPI definition can be extended with specification extensions:
* https://www.asyncapi.com/docs/specifications/v2.0.0#specificationExtensions
* "x-" prefix for new fields
* the custom fields are currently not rendered by the React component!

### Diff library
The diff library is in an early stage of development and aims to enable comparison and compatibility of API changes.
* https://github.com/asyncapi/diff

### AsyncAPI Studio
Studio is currently in an early stage of development and wants to offer an editor (similar to the playground)
and potentially some other functionalities (?).
* https://github.com/asyncapi/studio

### Modelina
As well in an early stage of development. Did not reach version 1.0.0 yet and therefore, breaking changes 
are still possible. Modelina SDK wants to enable generation of data models from JSON and AsyncAPI definitions.
* https://github.com/asyncapi/modelina
* seems to only support JavaScript as output language (?)
