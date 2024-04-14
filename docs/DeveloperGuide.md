---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# EstateEase Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**
This project is based on the AddressBook-Level3 project created by the
[SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` or `House` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

<div style="page-break-after: always;"></div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddSellerCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddSellerCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddSellerCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="800" />

<div style="page-break-after: always;"></div>

The `Model` component,

* stores EstateEase data i.e., all `Person` objects and `House` objects (which are contained in a `UniquePersonList` object and `UniqueHouseList` object respectively).
* stores the currently 'selected' `Person` objects and `House` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` and `ObservableList<House>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save EstateEase data (which includes `Buyer`, `Seller`, and `House`) and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

[//]: # (@@author KhoonSun47)
### Malformed JSONs
<box type="warning" seamless>

**Warning:** If the JSON file is malformed, the contents of EstateEase will not be loaded, and a log message will be printed to a log file instead of being displayed within the application itself. <br><br> No proactive measures are taken to rectify this issue, such as deleting the corrupted file immediately upon detection or automatically fixing the fields or values in the malformed JSON. <br><br> While we do not assume the intended action for encountering a malformed JSON file, it should be noted that the malformed JSON will eventually be overwritten with a correctly formatted JSON once a valid command is triggered.

</box>

[//]: # (@@author)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

[//]: # (@@author KhoonSun47)
### Adding Seller

###### Purpose
This `addSeller` feature allows user to add a `Seller` and a `House` into EstateEase.

###### Example Usage Scenario
The following activity diagram summarizes what happens when a user executes the `addSeller` command.

<puml src="diagrams/AddSellerActivityDiagram.puml" width="700" />

###### Implementation
The following sequence diagram shows how an `addSeller` operation goes through the `Logic` component:

<puml src="diagrams/AddSellerSequenceDiagram-Logic.puml" alt="AddSellerSequenceDiagram-Logic" width="1200"/>

<box type="info" seamless>

**Take Note of the Parameters for Sequence Diagram:**

- `argumentA`: addSeller n/John Doe p/98765432 e/johnd@example.com type/Hdb street/Clementi Ave 2 blk/311 level/02 unitNo/25 postal/578578 price/999999999
- `argumentB`: n/John Doe p/98765432 e/johnd@example.com type/Hdb street/Clementi Ave 2 blk/311 level/02 unitNo/25 postal/578578 price/999999999
- `argumentC`: type/Hdb street/Clementi Ave 2 blk/311 level/02 unitNo/25 postal/578578 price/999999999
</box>


The proposed add seller mechanism is facilitated by `Person`. It extends `Person` with additional field `House`.
Additionally, it implements the following operations:
* `Seller#addHouse()` — Add a house to the seller list of houses.
* `Seller#removeHouse()` — Remove a house from the list of houses of the seller.
* `Seller#getHouses()` — Get a list of houses from the seller.
* `Seller#hasHouse()` — Check if a house is in the list of houses from the seller.
* `Seller#copy()` — Values of the seller is copied to a new seller object.

**Details:**
1. The `AddSellerCommand` class extends the `Command` class and is responsible for executing the add seller process. It expects the full details of the `Seller` and `House` to be specified in the command input.
2. Upon execution, the command will then be parsed to `execute()` in `LogicManager`.
3. The command will then be parsed to `parseCommand()` in `AddressBookParser`.
4. The argument which contains a `Seller` and a `House` will then be parsed to `parse()` in `AddSellerCommandParser`.
5. The `House` will then be checked at the `checkValidity()` in `AddHouseCommandParser`.
6. If all the arguments for a `Seller` and a `House` is valid, it will then be parsed to the `AddSellerCommand`, where a constructor will be created.
7. At the `AddSellerCommand`, it will check whether there is duplicate `Seller` in `Person`, `Seller` and `Buyer` cannot be the same `Person`. The `House` will also be checked to see whether it is a duplicate `House`, since the same `House` should not exist in the EstateEase data.
8. Once the checks are all done, a `CommandResult` will then be returned. The system will then construct a new `Seller` object which contains the `Seller` details and `House` details. This object will then be used to update the `Model` through `addPerson()` method of model.

**Note:**
- If the `Seller` has the same name as a `Seller` or a `Buyer`, it will return an error to the user that the `Person` has existed. Each `Buyer` and `Seller` are unique, and `Buyer` cannot be a `Seller`, and vice versa.
- If there is a duplicate `House` in the EstateEase, it will return an error to the user that the should `House` has existed. Each `House` is unique, and there should not be duplicates.

###### Design Considerations
**Aspect: How `addSeller` executes:**

* **Alternative 1 (current choice):** Use a new command to add `Seller`.
    * **Pros:** Easier to implement, lesser confusion on adding `Seller` and `Buyer`.
    * **Cons:** May lead to many commands, which is difficult for user to remember.

* **Alternative 2:** Use a prefix to differentiate between `Seller` and `Buyer`.
    * **Pros:** Having lesser commands is easier for the user to remember.
    * **Cons:** Difficult to implement, having more prefixes means more validation.

* **Alternative 1 (current choice):** Use the `addSeller` command to add one `Seller` and one `House`.
    * **Pros:** It allows the `Seller` to have at least one house, so that `Seller` and `Buyer` can match instantly (if the requirements matches).
    * **Cons:** Difficult to implement, as there needs to be validation on both `Seller` and `House`.

* **Alternative 2:** Use the `addSeller` command to add only `Seller`.
    * **Pros:** It is easier to implement, because it does not require `House` validation.
    * **Cons:** The `Seller` will not have a house, and if all the `Seller` in the list does not have a house, `matchBuyer` cannot happen.

<div style="page-break-after: always;"></div>

[//]: # (@@author zengzihui)
### Adding Buyer

###### Purpose
This `addBuyer` feature allows user to add a `Buyer` into the EstateEase

###### Example Usage Scenario
The following activity diagram summarizes what happens when a user executes the `addBuyer` command

<puml src="diagrams/AddBuyerActivityDiagram.puml" width="1000" />

###### Implementation
The following sequence diagram shows how an `addBuyer` operation goes through the `Logic` component:

<puml src="diagrams/AddBuyerSequenceDiagram-Logic.puml" alt="AddBuyerSequenceDiagram-Logic"/>

The proposed add buyer mechanism is facilitated by `Person`. It extends `Person` with additional field `Budget` and `PreferredHousingType`.

**Details:**
1. The `AddBuyerCommand` class extends the `Command` class and is responsible for executing the add buyer process. It expects the full name and full details of the `Buyer` to be specified in the command input.
2. Upon execution, the command will then be parsed to `execute()` in `LogicManager`.
3. The command will then be parsed to `parseCommand()` in `AddressBookParser`.
4. The argument which contains a `Buyer` will then be parsed to `parse()` in `AddBuyerCommandParser`.
6. If all the arguments for a `Buyer` are valid, it will then be parsed to the `AddBuyerCommand`, where a constructor will be created.
7. At the `AddBuyerCommand`, it will check whether there is duplicate `Seller` or `Buyer` in `Person`, `Seller` and `Buyer` cannot be the same `Person`.
8. Once the checks are all done, a `CommandResult` will then be returned. The system will then construct a new `Buyer` object which contains the `Buyer` details. This object will then be used to update the `Model` through `addPerson()` method of model.

**Note:**
- If the `Buyer` has the same name as a `Seller` or a `Buyer`, it will return an error to the user that the `Person` has existed. Each `Buyer` and `Seller` are unique, and `Buyer` cannot be a `Seller`, and vice versa.

###### Design Considerations
**Aspect: How `addBuyer` executes:**

* **Alternative 1 (current choice):** Use a new command to add `Buyer`.
    * **Pros:** Easy to implement, lesser confusion on adding `Seller` and `Buyer`.
    * **Cons:** May lead to many commands, which is difficult for user to remember.

* **Alternative 2:** Use a prefix to differentiate between `Seller` and `Buyer`.
    * **Pros:** Having lesser commands is easier for the user to remember.
    * **Cons:** Difficult to implement, having more prefixes means more validation.

<div style="page-break-after: always;"></div>

[//]: # (@@author zengzihui)

### Viewing Person

###### Purpose
This `view` feature allows user to view the details of a `Person` in EstateEase.

###### Implementation
The following sequence diagram shows how an `view` operation goes through the `Logic` component:

<puml src="diagrams/ViewSequenceDiagram-Logic.puml" alt="ViewSequenceDiagram-Logic"/>

**Details:**
1. The `ViewCommand` class extends the `Command` class and is responsible for executing the view person details process. It expects the index of the person in the displayed list to be specified in the command input.
2. Upon execution, the command will then be parsed to `execute()` in `LogicManager`.
3. The command will then be parsed to `parseCommand()` in `AddressBookParser`.
4. The argument which contains a `INDEX` will then be parsed to `parse()` in `ViewCommandParser`.
6. If the `INDEX` is valid, it will then be parsed to the `ViewCommand`, where a constructor will be created.
7. At the `ViewCommand`, it will check whether `INDEX` is within the range of the displayed list.
8. Once the checks are all done, a `CommandResult` will then be returned. The system will then construct a new `Person` object which contains the `Person` details. This object will then be used to update the `Model` through `showPerson()` method of model.

**Note:**
- The `INDEX` should be the index of the displayed person list.

###### Design Considerations
**Aspect: How `view` executes:**

* **Alternative 1 (current choice):** Use a new command to view for both `Buyer` and `Seller` and system will check which object to display.
    * **Pros:** Having lesser commands, lesser confusion on adding too many commands for user to remember.
    * **Cons:** May be messy to implement it.



* **Alternative 2:** Use 2 commands to differentiate between `Seller` and `Buyer`.
    * **Pros:** Clear commands for user to view either `Seller` or `Buyer`.
    * **Cons:** Having too many commands is troublesome for the user to remember.


* **Alternative 1 (current choice):** Use `INDEX` in the command input to select the person to view details.
    * **Pros:** Easier for input validation checks, easier to implement.
    * **Cons:** When there is too many `Person` added to EstateEase, it might not be easy to find the index of the person the user want to view details..


* **Alternative 2:** Use `FULL_NAME` in the command input to select the person to view details.
    * **Pros:** Save user the trouble to search for index of the person that user want to view.
    * **Cons:** More input validation has to be done, user might not remember the full name of the person if the full name is too long.

<div style="page-break-after: always;"></div>

[//]: # (@@author lokidoki102)

### Editing Buyer

###### Purpose
The `editBuyer` command allows users to edit the details an existing `Buyer` in EstateEase.

###### Implementation

<puml src="diagrams/EditBuyerSequenceDiagram.puml" alt="EditBuyerSequenceDiagram" width="1200"/>

1. The user enters the `editBuyer` command in the format `editBuyer INDEX [n/NAME] [p/PHONE] [e/EMAIL]
[type/HOUSING_TYPE] [budget/BUDGET]` (E.g. editBuyer 1 p/91234567 e/johndoe@example.com).
2. The input is then passed to the `AddressBookParser` which calls `EditBuyerCommandParser.parse()` to parse the input.
   If the input is invalid, this method will throw a `ParseException`, prompting the user where the invalid input went
   wrong.
3. `EditBuyerCommandParser.parse()` will create an `EditBuyerDescriptor` object if the input is valid.
    The `EditBuyerDescriptor` object contains the edited values of the `Buyer`.
    `EditBuyerCommandParser.parse()` will then return an `EditBuyerCommand` object which contains the `INDEX` of the
    `Buyer` and `EditBuyerDescriptor`.
4. The logic manager will then `execute()` of the `EditBuyerCommand` object.
5. In the `execute()`, the system will check if the `INDEX` is valid, check if the object being edited is of `Buyer`
   type, and check if the edited `NAME` value already exists in EstateEase. If any of these checks fail, a
   `CommandException` will be thrown.
6. Once the checks are all done, the system will construct a new `Buyer` object which contains the edited values. This
   object will then be used to update the model through `setPerson()` method of `model`.

###### Design Considerations
It is important to ensure that the target of the `editBuyer` command is in fact a `Buyer` object as it has different
parameters that is not available to `Seller` object. Hence, the reason why the edit command is also separated into two
commands, one for buyer and one for seller. The uniqueness of the `NAME` value in the EstateEase is also
needed as some of the commands uses the `NAME` to execute the command.

### Editing Seller

###### Purpose
The `editSeller` command allows user to edit the details of an existing `Seller` in EstateEase.

###### Implementation
The overall implementation of this command is very similar to `editBuyer` command, except the command format is
`editSeller [n/NAME] [p/PHONE] [e/EMAIL]` (E.g. editSeller 1 p/91234567 e/johndoe@example.com).

###### Why It's Implemented That Way
- The edit function is separated out into Buyer and Seller as each Buyer and Seller have a minor difference in their attributes.

<div style="page-break-after: always;"></div>

[//]: # (@@author felixchanyy)
### Matching Sellers to a Buyer

###### Purpose

The real estate agent may want to obtain all houses from sellers that match the buyer's preferences. For example, the real estate agent may want to gather all houses from sellers that align with a specified buyer's budget and preferred housing type.

###### Example Usage Scenario:

The following activity diagram summarises the execution of a `matchBuyer` command:

<puml src="diagrams/MatchBuyerActivityDiagram.puml" alt="MatchBuyerActivityDiagram" width="800"/>

**Step 1:** The user launches the application for the first time. EstateEase will be initialized with the initial app state (consisting of both `Buyer` and `Seller` details).

**Step 2:** The user executes the `matchBuyer Alice Lim` command to find and display `Seller` details with `House` that match the preferences of the buyer named "Alice Lim" in EstateEase.

**Note:** If the `matchBuyer` command is used without specifying `FULL_NAME` of a `Buyer`, it will return a message to the user indicating that the buyer does not exist.

###### Implementation

The following sequence diagram shows how an `matchBuyer` operation goes through the `Logic` component:

<puml src="diagrams/MatchBuyerSequenceDiagram-Logic.puml" alt="MatchBuyerSequenceDiagram-Logic" width="1200"/>

1. The `MatchBuyerCommand` class extends the `Command` class and is responsible for executing the matching process. It expects the full name of the `Buyer` to be specified in the command input. Upon execution, the command retrieves the `Budget` and `HousingType` of the specified buyer. It then matches these preferences with the listings of available sellers' houses.

2. The `MatchBuyerCommandParser` class is used to parse the user input and create the `MatchBuyerCommand` object. When executed by the `LogicManager`, the `MatchBuyerCommand#execute(Model model)` method is called. This method matches the buyer's preferences with available sellers' houses in the model and returns a `CommandResult` object.

###### Design Considerations

* **Alternative 1 (current choice):** Use a new `MatchBuyerCommand` to do matching.
    * **Pros:**
        * Provides a dedicated command specifically tailored for matching sellers to a `Buyer`, enhancing clarity and readability in the codebase.
        * Allows for specialized handling of buyer matching logic within its own command class, facilitating easier maintenance and updates.
    * **Cons:**
        * Requires the introduction of a new command class, potentially increasing the overall complexity of the codebase.
        * More commands for the user to remember
    
* **Alternative 2:** Use prefix in `FindCommand` to do matching.
    * **Pros:**
        * Utilizes an existing command class, reducing the need for additional code and command classes dedicated to matching.
        * Leverages the flexibility of the `FindCommand` structure to accommodate various matching scenarios with different prefixes.
    * **Cons:**
        * May lead to less clear and focused command implementations, as matching logic would be mixed with other find functionalities.
        * Could result in increased complexity within the `FindCommand` class, potentially making it harder to maintain and understand.

<div style="page-break-after: always;"></div>

[//]: # (@@author redcolorbicycle)
### Adding Houses

###### Purpose

The `addHouse` Command is necessary to allow Houses to be added to Sellers.

###### Example Usage Scenario:

The following sequence diagram shows how an `addHouse` operation goes through the `Logic` component:

<puml src="diagrams/AddHouseSequenceDiagram-Logic.puml" alt="AddHouseSequenceDiagram-Logic" width="1200"/>

**Step 1:** The user launches the application. The `AddressBook` is assumed to already have the `Seller` John Doe.

**Step 2:** The user executes the `addHouse n/John Doe type/Condominium street/Clementi Ave 2 blk/N/A level/02 unitNo/25 postal/578578 price/99999 ` command to add a `House` with these details to `AddressBook`.

**Note:** If the `addHouse` command is used with a `Person` who is not a `Seller`, or with invalid house details, or to a nonexistent `Seller`, or an already existing `House`, an error message is displayed.

###### Implementation

The `AddHouseCommand` class extends the `Command` class and is responsible for executing the adding of a house to a seller. It expects the full name of the seller to be specified in the command input, along with the full details of the house. Upon execution, the command fetches listings of available sellers' houses. It checks if the house already exists and if the seller is a valid seller. If it does not exist and the seller is valid, the house is added to the seller.

The `AddHouseCommandParser` class is used to parse the user input and create the `AddHouseCommand` object. When executed by the `LogicManager`, the `AddHouseCommand#execute(Model model)` method is called. This method checks if the seller exists and if the house already exists and if the house is valid and returns a `CommandResult` object.

###### Design Considerations

* **Alternative 1 (current choice):** Use only a `Houses` ArrayList within Sellers to track
    * **Pros:**
        * Allows for only seller handling for houses and reduces overlap within classes
        * Easier to track house logic as it will be contained within the seller
    * **Cons:**
        * Need to check all sellers whenever houses are checked for duplicates. increasing runtime

<div style="page-break-after: always;"></div>

[//]: # (@@author redcolorbicycle)
### Deleting Houses

###### Purpose

The `deleteHouse` Command is necessary to delete Houses from relevant Sellers.

###### Example Usage Scenario:

The following sequence diagram shows how an `deleteHouse` operation goes through the `Logic` component:

<puml src="diagrams/DeleteHouseSequenceDiagram-Logic.puml" alt="DeleteHouseSequenceDiagram-Logic" width="1200"/>

**Step 1:** The user launches the application. The `AddressBook` is assumed to already have the `Seller` John Doe. John Doe is assumed to have a Condominium located at Clementi Ave 2, level 2 (with no block), unit number 25, postal code 578578 with price 99999.

**Step 2:** The user executes the `deleteHouse n/John Doe type/Condominium street/Clementi Ave 2 blk/N/A level/02 unitNo/25 postal/578578 price/99999 ` command to delete a `House` with these details from `AddressBook` and John Doe's houses.

**Note:** If the `deleteHouse` command is used with a `Person` who is not a `Seller`, or with invalid house details, or to a nonexistent `Seller`, or an already existing `House` not under the named `Seller`, an error message is displayed.

###### Implementation

The `DeleteHouseCommand` class extends the `Command` class and is responsible for executing the deletion of a house from a seller. It expects the full name of the seller to be specified in the command input, along with the full details of the house. Upon execution, the command fetches listings of available sellers' houses. It checks if the house already exists and if the seller is a valid seller. If it does exist and the seller is valid, the house is deleted from the seller.

The `DeleteHouseCommandParser` class is used to parse the user input and create the `DeleteHouseCommand` object. When executed by the `LogicManager`, the `DeleteHouseCommand#execute(Model model)` method is called. This method checks if the seller exists and if the house already exists and if the house is valid and returns a `CommandResult` object.

###### Design Considerations

* **Alternative 1 (current choice):** Use only a `Houses` ArrayList within Sellers to track
    * **Pros:**
        * Allows for only seller handling for houses and reduces overlap within classes
        * Easier to track house logic as it will be contained within the seller
    * **Cons:**
        * Need to check all sellers whenever houses are checked for duplicates. increasing runtime

[//]: # (@@author)

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix: Requirements**

### Product scope

**Target user profile**:
* Residential Property Real Estate Listing Agent in Singapore
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: EstateEase simplifies residential property management for real estate listing agents in Singapore. With intuitive tools for listing and client communication, the app is tailored for efficiency. Agents can quickly access contacts and prioritize them, ensuring swift connections with clients.

<div style="page-break-after: always;"></div>

### User stories

Priorities: Urgent (must-must have) - `* * * *`, High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

[//]: # (@@author zengzihui)
| Priority  | As a …​                     | I want to …​                                                         | So that I can…​                                                           |
|-----------|-----------------------------|----------------------------------------------------------------------|---------------------------------------------------------------------------|
| `* * * *` | real estate agent           | add seller                                                           | keep track of their contact details and the houses that they are selling  |
| `* * * *` | real estate agent           | add buyer                                                            | keep track of their contact details and requirements                      |
| `* * * *` | real estate agent           | view the list of all persons                                         | quickly find the contact I need                                           |
| `* * * *` | real estate agent           | delete the buyer/seller that I want to remove                        | remove outdated or irrelevant contacts                                    |
| `* * * *` | real estate agent           | be able to exit the program when I want to                           | close the application                                                     |
| `* * * *` | real estate agent           | be able to automatically save the data I added, changed, and deleted | load the data when I open the application, with the saved data, next time |
| `* * *`   | real estate agent           | keep track of my buyer's budgets                                     | efficiently match them with houses within their financial constraints     |
| `* * *`   | real estate agent           | keep track of the prices of the houses                               | better manage my buyers' requirements                                     |
| `* * *`   | real estate agent           | match buyers with sellers based on the buyers' requirements          | quickly identify houses that align with their preferences.                |
| `* * *`   | real estate agent           | find for a specific contact                                          | access their details without scrolling through a long list                |
| `* * *`   | real estate agent           | easily update or modify existing contact information                 | have accurate and up-to-date records                                      |
| `* * *`   | real estate agent           | add new houses to the sellers                                        | keep track of the houses that the sellers have                            |
| `* * *`   | real estate agent           | delete existing houses from the sellers house list                   | remove irrelevant or incorrect houses                                     |
| `* * *`   | real estate agent           | have whatever EstateEase data I add load to the laptop I am using    | do not need to re-enter all the details whenever I open the app           |
| `* *`     | busy real estate agent      | be able to view specific buyer's requirements                        | understand what are their needs quickly                                   |
| `* *`     | busy real estate agent      | be able to view specific seller's properties                         | effectively assess their listings quickly                                 |
| `* *`     | busy real estate agent      | be able to tell at a glance whether the contact is a buyer or seller | do not need to remember their identity                                    |

[//]: # (@@author )

<div style="page-break-after: always;"></div>

### Use cases

(For all use cases below, the **System** is the `EstateEase` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a seller to person list**

**MSS:**

1. User chooses to add seller.
2. EstateEase requests for the details of the seller.
3. User enters the requested details.
4. Include Use Case UC03 (Add house to seller) for the first house.
4. EstateEase adds the seller and displays the newly added seller along with one house. <br>
   Use case ends.

**Extensions**
* 3a. User enters an invalid command. <br>
    * 3a1. EstateEase shows an error message. <br>
      Use case ends.

* 3b. User enters a seller that already exists. <br>
    * 3b1. EstateEase shows an error message. <br>
      Use case ends.

* 3c. User does not enter a required field. <br>
    * 3c1. EstateEase shows an error message. <br>
      Use case ends.

* 3d. User enters an invalid value. <br>
    * 3d1. EstateEase shows an error message. <br>
      Use case ends.

**Use case: UC02 - Add a buyer to person list**

**MSS:**

1. User chooses to add buyer.
2. EstateEase requests for the details of the buyer.
3. User enters the requested details.
4. EstateEase adds the buyer and displays the newly added buyer. <br>
   Use case ends.

**Extensions**
* 3a. User enters an invalid command. <br>
    * 3a1. EstateEase shows an error message. <br>
      Use case ends.

* 3b. User enters a buyer that already exists. <br>
    * 3b1. EstateEase shows an error message. <br>
      Use case ends.

* 3c. User does not enter a required field. <br>
    * 3c1. EstateEase shows an error message. <br>
      Use case ends.

* 3d. User enters an invalid value. <br>
    * 3d1. EstateEase shows an error message. <br>
      Use case ends.

**Use case: UC03 - Add house to seller**

**MSS:**

1. User chooses to add new house to seller.
2. EstateEase requests for the details of the house.
3. User enters requested details.
4. EstateEase adds the new house and displays the newly added house of the seller. <br>
   Use case ends.

**Extensions**
* 1a. The EstateEase list does not have any seller. <br>
    * 1a1. EstateEase shows an error message. <br>
      Use case ends.

* 3a. User enters duplicate house data. <br>
    * 3a1. EstateEase shows an error message. <br>
      Use case ends.

* 3b. User enters an invalid command. <br>
    * 3b1. EstateEase shows an error message. <br>
      Use case ends.

* 3c. User does not enter the required field. <br>
    * 3c1. EstateEase shows an error message. <br>
      Use case ends.

* 3d. User enters an invalid seller. <br>
    * 3d1. EstateEase shows an error message. <br>
      Use case ends.

* 3e. User enters an invalid house. <br>
    * 3e1. EstateEase shows an error message. <br>
      Use case ends.

[//]: # (@@author lokidoki102)
**Use case: UC04 - List all persons**

**MSS:**

1.  User requests to list all of his/her persons.
2.  EstateEase displays a list of persons, each with their details
    and an indication of whether they are a buyer or seller.

    Use case ends.

**Extensions**

* 2a. The list is empty.
    * 2a1. EstateEase displays a message stating that the list is empty.

      Use case ends.

[//]: # (@@author )

[//]: # (@@author zengzihui)
**Use case: UC05 - Delete a person**

**MSS:**

1.  User requests to <u>list all person (UC04)</u>.
2.  User requests to delete a specific person in the person list.
3.  EstateEase deletes the person.

    Use case ends.

**Extensions**

* 1a. The person list is empty.

  Use case ends.

* 2a. The given index is invalid input type.
    * 2a1. EstateEase shows an error message regarding the invalid input type.

      Use case resumes at step 1.

* 2b. The given index is out of range.
    * 2b1. EstateEase shows an error message regarding the out of range.

      Use case resumes at step 1.

[//]: # (@@author )

[//]: # (@@author KhoonSun47)
**Use case: UC06 - Load EstateEase data from file**

**Actor: EstateEase**

**Preconditions:**
- EstateEase is initialized.
- The user starts the application.

**MSS:**

1. EstateEase automatically attempts to load EstateEase data from a JSON file located in the "data" folder at the same directory level as the application.
2. EstateEase parses the JSON file and imports the EstateEase data into the application's memory.
3. EstateEase displays the EstateEase data to the user. <br>
   Use case ends.

**Extensions**

* 1a. JSON file is missing or the "data" folder is missing:
    * 1a1. EstateEase retrieves sample data.
    * 1a2. EstateEase displays the sample data to the user.
    * 1a3. EstateEase waits for a valid command to be executed before creating an empty JSON file or the "data" folder as needed. <br>
    Use case ends.

* 1b. JSON file has an incorrect format or fields:
  * 1b1. EstateEase initializes an empty person list without displaying an error message.
  * 1b2. EstateEase displays an empty person list to the user.
  * 1b3. Any valid command to empty person list data trigger the creation of a new, corrected JSON file. <br>
  Use case ends.

[//]: # (@@author )

[//]: # (@@author KhoonSun47)
**Use case: UC07 - Save to storage**

**Actor: EstateEase**

**Preconditions:**
- The user initiates a command that modifies the EstateEase data.**

**MSS:**

1.  EstateEase processes the command <u>(UC01, UC02, UC03, UC05, UC011, UC012)</u> and updates EstateEase accordingly.
2.  EstateEase updates the JSON file with the new changes.
3.  EstateEase successfully updates the JSON file.

    Use case ends.

**Extensions**

* 2a. EstateEase is unable to write to the JSON file due to file permission issue.
    * 2a1. EstateEase shows error message regarding the insufficient file permission to the user. <br>
      Use case ends.

* 2b. EstateEase is unable to write to the JSON file due to some IOException.
    * 2b1. EstateEase shows error message regarding the IOException to the user. <br>
      Use case ends.

[//]: # (@@author )

[//]: # (@@author zengzihui)
**Use case: UC08 - Search a person**

**MSS:**

1. User requests to search for a person.
2. EstateEase displays all the person that match the inputted person name.

   Use case ends.

**Extensions**

* 1a. The given person name does not match any person names in the person list.
    * 1a1. EstateEase shows an error message indicating no matches found.

      Use case ends.

**Use case: UC09 - View a buyer's requirements**

**MSS:**

1. User requests to view a specific buyer's requirements.
2. EstateEase displays the buyer's personal details with their requirements.
   Use case ends.

**Extensions**

* 2a. EstateEase detects an invalid index.
    *   2a1. EstateEase shows an error message regarding an invalid entry.
        Use case ends.
* 2b. Command does not match EstateEase's registered command spelling.
    *   2b1. EstateEase shows an error message regarding an invalid command.
        Use case ends.

**Use case: UC10 - View a seller's houses**

**MSS:**

1. User requests to view a specific seller's houses.
2. EstateEase displays the seller's personal details with their houses details.

   Use case ends.

**Extensions**

* 2a. EstateEase detects an invalid index.
    * 2a1. EstateEase shows an error message regarding an invalid entry.

      Use case ends.
* 2b. Command does not match EstateEase's registered command spelling.
    * 2b1. EstateEase shows an error message regarding an invalid command.

      Use case ends.

[//]: # (@@author )

[//]: # (@@author lokidoki102)
**Use case: UC11 - Edit buyer details**

**MSS:**

1.  User executes the command to edit a buyer in EstateEase.
2.  EstateEase updates the details of the specific buyer selected by the user.

    Use case ends.

**Extensions**

* 1a. The given index is invalid.
    * 1a1. EstateEase shows an error message.

      Use case ends.

* 1b. The new value for the field being updated is not valid.
    * 1b1. EstateEase shows format error message.

      Use case ends.

* 1c. The edited name field already exists in EstateEase.
    * 1c1. EstateEase error message, indicating that the person already exists.

      Use case ends.

**Use case: UC12 - Edit seller details** <br>
This use case is similar to <u>UC11 - Edit buyer details</u>, except it takes in different field
(i.e. parameters for name, phone, and email).

[//]: # (@@author )

**Use case: UC13 - Match sellers with buyer's preferences**

**MSS:**
1. User requests to match a buyer's preferences.
2. EstateEase retrieves the budget and preferred housing type of the specified buyer.
3. EstateEase matches the buyer's preferences with the listings of available sellers.
4. EstateEase displays the list of sellers who have houses matching the specified buyer's budget and housing type.

   Use case ends.

**Extensions**

* 1a. The specified buyer does not exist
    * 1a1. EstateEase shows a message the specified buyer does not exist.

      Use case ends.

* 3a. There are no matching listings
    * 3a1. EstateEase shows a message indicating there is no matching results.

      Use case ends.

**Use case: UC14 - Exit application**

**MSS:**

1. User enters the 'exit' command.
2. EstateEase immediately closes the application.

   Use case ends.

**Extensions**

* 1a. User enters an unrecognized command.
    * 1a1. EstateEase displays a message "Unknown command".
      Use case resumes from the previous step.

<div style="page-break-after: always;"></div>

### Non-Functional Requirements

| ID | Requirement                                                                                                                             |
|----|-----------------------------------------------------------------------------------------------------------------------------------------|
| 1  | The application must operate on Windows, Linux, and MacOS platforms provided Java `11` is installed.                                    |
| 2  | The system should support up to 1000 persons and 1000 houses without experiencing performance lags during typical usage.                |
| 3  | Users with above-average typing speeds should be able to perform most tasks more efficiently using keyboard commands than with a mouse. |
| 4  | The application should function properly both online and offline.                                                                       |
| 5  | All data should load and the application should respond within three seconds.                                                           |
| 6  | Actions such as adding, updating, or deleting person records must complete within three seconds.                                        |
| 7  | The response time for adding or deleting a house must also be within three seconds.                                                     |
| 8  | The user interface should be intuitive enough for users unfamiliar with similar applications.                                           |
| 9  | The software must maintain a reliability rate of 99% across various devices and operating systems.                                      |
| 10 | The system is designed to support only one user at a time.                                                                              |
| 11 | Data should be stored locally on the user's device.                                                                                     |
| 12 | The application should automatically recover from common errors without crashing or requiring user intervention.                        |
| 13 | The program must provide meaningful error messages either in the application or terminal to help users resolve issues.                  |

<div style="page-break-after: always;"></div>

[//]: # (@@author KhoonSun47)
### Glossary

| ID | Term                   | Definitions                                                                                                                                                  |
|----|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1  | Index                  | Represents the position of a person within the displayed list.                                                                                               |
| 2  | Hdb                    | Hdb refers to Housing Development Board flats, which are public housing units in Singapore designed to be affordable and accessible to the general populace. |
| 3  | Condominium            | A condominium is a type of private residence in a building or community complex with shared amenities such as pools, gyms, and security.                     |
| 4  | Landed                 | Landed property refers to residential real estate that includes both the house and the land on which it stands.                                              |
| 5  | Buyer                  | An individual interested in purchasing a house.                                                                                                              |
| 6  | Seller                 | An individual looking to sell a house, who may own anywhere from zero to multiple properties.                                                                |
| 7  | House                  | Refers to a property owned by a seller, defined by its price and type, which are used by EstateEase to match with a buyer's preferences.                     |
| 8  | Budget                 | Refers to the amount a buyer is willing to pay for a house.                                                                                                  |
| 9  | Price                  | Refers to the amount a seller is willing to sell the house for.                                                                                              |
| 10 | Preferred Housing Type | The type of house a buyer is seeking.                                                                                                                        |
| 11 | Housing Type           | The type of house being sold by the seller.                                                                                                                  |
| 12 | Person                 | A person can be classified as either a buyer or a seller.                                                                                                    |

[//]: # (@@author )

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix A: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. **Initial launch**

    1. Download the jar file and copy into an empty folder

    2. Double-click the jar file <br>
       Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum. <br><br>

2. **Saving window preferences**

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

[//]: # (@@author KhoonSun47)
### Loading Data
1. **Dealing with Missing Data Folder**

    1. **Test case:** Manually delete the `data` folder. <br>
       **Expected:** The application automatically populates EstateEase with sample data, displaying buyers and sellers where sellers are associated with houses. <br><br>
   
2.  **Dealing with Missing Data File**

    1. **Test case:** Manually delete the `addressbook.json` file. <br>
       **Expected:** The application automatically populates EstateEase with sample data, displaying buyers and sellers where sellers are associated with houses. <br><br>

3. **Dealing with Corrupted Data Files**

    1. **Test case:** Duplicate a buyer or seller's details (`name`, `phone`, and `email`) and use them for the opposite role (e.g., use a buyer details for a seller or vice versa). <br>
       **Expected:** This action violates EstateEase's constraints against duplicate persons, resulting in a corrupted `addressbook.json`. The application should detect this error and display an empty EstateEase. <br><br>

    2. **Test case:** Copy a house listed under one seller and duplicate it under another seller's list of houses. <br>
       **Expected:** This action violates EstateEase's constraints against duplicate houses, resulting in a corrupted `addressbook.json`. The application should detect this error and display an empty EstateEase. <br><br>

    3. **Test case:** Make a data file to have the same name as `addressbook.json` but with an incorrect format. <br>
       **Expected:** This action violates EstateEase's constraints against incorrect data formats, resulting in a corrupted `addressbook.json`. The application should detect this error and display an empty EstateEase. <br>

[//]: # (@@author KhoonSun47)
### Adding a Seller

**Prerequisites:** 
- List all persons using the `list` command. 
- There is currently no `person` with the name "John Carl 1", "John Felix", "John Carl 2", "Carl Lim Jovi Rato", "Carl Lim". <br>

1. **Add seller**
   1. **Test case:** `addSeller n/John Carl 1 p/98765432 e/johncarl1@example.com type/Hdb street/Clementi Ave 2 blk/311 level/02 unitNo/25 postal/578578 price/999999999`<br>
      **Expected:** A new seller is added, with name `John Carl 1`, phone `98765432`, email `johncarl@example.com` and `Hdb` house details with street `Clementi Ave 2`, with block `311`, with level `02`, with unit number `25`, with postal code `578578` and price `999999999`. <br><br>

   2. **Test case:** `addSeller n/John Felix p/98765433 e/johnfelix@example.com type/Condominium street/Clementi Ave 3 blk/N/A level/03 unitNo/26 postal/578579 price/100000`<br>
      **Expected:** A new seller is added, with name `John Felix`, phone `98765433`, email `johnfelix@example.com` and `Condominium` house details with street `Clementi Ave 3`, with block `N/A`, with level `03`, with unit number `26`, with postal code `578579` and price `100000`. <br><br>

   3. **Test case:** `addSeller n/John Carl 2 p/98765434 e/johncarl2@example.com type/Landed street/Clementi Ave 4 unitNo/26 postal/578580 price/1000000`<br>
      **Expected:** A new seller is added, with name `John Carl 2`, phone `98765434`, email `johncarl2@example.com` and `Landed` house details with street `Clementi Ave 4`, with unit number `26`, with postal code `578580` and price `1000000`. <br><br>

2. **Invalid format**
   
   1. **Test case:** `addSeller n/ p/98765432 e/johncarl1@example.com type/Hdb street/Clementi Ave 10 blk/302 level/12 unitNo/29 postal/578978 price/999999999` <br>
      **Expected:** No seller is added. Error indicating name should not be blank. <br><br>

   2. **Test case:** `addSeller n/Carl Lim Jovi Rato p/9876-9999 e/carllimjovirato@example.com type/Hdb street/Toa Payoh Ave 10 blk/312 level/22 unitNo/39 postal/528978 price/9999999` <br>
      **Expected:** No seller is added. Error indicating that phone should only contain numbers. <br><br>

   3. **Test case:** `addSeller n/Carl Lim Jovi Rato p/98769999 e/carllimjovirato-example.com type/Hdb street/Toa Payoh Ave 10 blk/312 level/22 unitNo/39 postal/528978 price/9999999` <br>
      **Expected:** No seller is added. Error indicating that email should be in the format of local-part@domain. <br><br>

3. **Invalid (Duplicate)**
   1. **Test case:** `addSeller n/John Carl 1 p/98765432 e/johncarl1@example.com type/Landed street/Clementi Ave 2 unitNo/25 postal/578578 price/10000` <br>
      **Expected:** No seller is added. Error indicating that the person already existed in the data. <br><br>

   2. **Test case:** `addSeller n/Carl Lim p/98765432 e/johncarl@example.com type/Hdb street/Clementi Ave 2 blk/311 level/02 unitNo/25 postal/578578 price/999999999` <br>
      **Expected:** No seller is added. Error indicating that the house already existed in the data. <br>

[//]: # (@@author zengzihui)
### Adding a Buyer

**Prerequisites:**
- List all persons using the `list` command.
- There is currently no `person` with the name "James Cook", "Kris Hua", "Grace Tan" and "Chris Ong". 
- "John Carl 1" exists as a seller in EstateEase.<br>

1. **Add buyer**
    1. **Test case:** `addBuyer n/James Cook p/98753432 e/jamescook@example.com budget/550000 type/Hdb`<br>
       **Expected:** A new buyer is added, with name `James Cook`, phone `98753432`, email `jamescook@example.com`, budget `550000`, preferred housing type `Hdb`<br><br>

2. **Invalid format**

    1. **Test case:** `addBuyer n/Kris Hua p/98765432 e/krishua@example.com budget/99999900` <br>
       **Expected:** No buyer is added. Error indicating invalid command format. <br><br>

3. **Invalid (Duplicate Person)**
    1. **Test case:** `addBuyer n/John Carl 1 p/98765432 e/johncarl1@example.com budget/550000 type/Hdb`
       **Expected:** No buyer is added. Error indicating that the person already existed in the data. <br><br>

4. **Invalid Value**
   1. **Test case:** `addBuyer n/Grace Tan p/98765432 e/gracetan@examaple.com budget/-99999900 type/Hdb` <br>
      **Expected:** No buyer is added. Error indicating budget should be a positive number. <br><br>

   2. **Test case:** `addBuyer n/Chris Ong p/98765432 e/chrisong@examaple.com budget/aa type/Hdb` <br>
      **Expected:** No buyer is added. Error indicating budget should be a positive number. <br>


[//]: # (@@author redcolorbicycle)
### Adding a House

**Prerequisites:** 
- There is a seller by the name of "John Felix".
- There is no seller by the name of "Lim Carl".
- A Condominium with Street "Clementi Ave 2", no Block, Level "02", Unit Number 25, Postal Code 578568 and Price of 99999 does not currently exist in EstateEase.
- A Condominium with Street "Clementi Ave 3", no Block, Level "03", Unit Number 26, Postal Code 578579 and Price of 100000 currently exists in EstateEase.

1. **Adding the house to the seller**

    1. **Test case:** `addHouse n/John Felix type/Condominium street/Clementi Ave 2 blk/N/A level/02 unitNo/25 postal/578568 price/99999`<br>
       **Expected:** New house added! <br><br>

2. **Invalid house format**

    1. **Test case:** `addHouse n/John Felix type/Condominium Ave 2 blk/N/A level/02 unitNo/25 postal/578568 price/99999`<br>
       **Expected:** Invalid command format! <br><br>

    2. **Test case:** `addHouse n/John Felix type/Condominium street/Clementi Ave 2 blk/N/A unitNo/25 postal/578568 price/99999`<br>
       **Expected:** Invalid command format! <br><br>

3. **Invalid seller**

    1. **Test case:** `addHouse n/Lim Carl type/Condominium street/Clementi Ave 2 blk/N/A level/02 unitNo/25 postal/578558 price/99999`<br>
       **Expected:** This Seller does not exist in EstateEase <br><br>

4. **House already exists**

    1. **Test case:** `addHouse n/John Felix type/Condominium street/Clementi Ave 3 blk/N/A level/03 unitNo/26 postal/578579 price/100000`<br>
       **Expected:** This house already exists in EstateEase <br>

[//]: # (@@author redcolorbicycle)
### Deleting a House

**Prerequisites:**
- There is a seller by the name of "John Felix".
- There is a seller by the name of "John Carl 1".
- There is no seller by the name of "Lim Carl".
- A Condominium with Street "Clementi Ave 2", no Block, Level "02", Unit Number 25, Postal Code 578568 and Price of 99999 does not currently exist in EstateEase.
- A Condominium with Street "Clementi Ave 3", no Block, Level "03", Unit Number 26, Postal Code 578579 and Price of 100000 currently exists in EstateEase and is owned by John Felix.

1. **Deleting the house from the seller**

    1. **Test case:** `deleteHouse n/John Felix type/Condominium street/Clementi Ave 3 blk/N/A level/03 unitNo/26 postal/578579 price/100000`<br>
       **Expected:** House deleted! <br><br>

1. **Deleting the house from the wrong seller**

    1. **Test case:** `deleteHouse n/John Carl 1 type/Condominium street/Clementi Ave 2 blk/N/A level/02 unitNo/25 postal/578568 price/99999`<br>
       **Expected:** This house does not belong to this seller! <br><br>

1. **House does not exist**

    1. **Test case:** `deleteHouse n/John Felix type/Condominium street/Clementi Ave 2 blk/N/A level/05 unitNo/25 postal/578538 price/99999`<br>
       **Expected:** This house does not exist in EstateEase <br><br>

1. **Invalid seller**

    1. **Test case:** `addHouse n/Lim Carl type/Condominium street/Clementi Ave 2 blk/N/A level/02 unitNo/25 postal/578578 price/99999`<br>
       **Expected:** This Seller does not exist in EstateEase <br>

[//]: # (@@author zengzihui)
### Viewing a Person

**Prerequisites:**
- List all persons using the `list` command. Make sure there are at least 1 person in the list. <br>

1. **Valid index**

   1. **Test case:** `view 1` <br>
      **Expected:** Details of the first person from the displayed list is displayed at the right side of the panel with the displayed person list at the left side of the panel. Name of the selected person shown in the status message. <br><br>
   
2. **Invalid index**

   1. **Test case:** `view 18` <br>
      **Expected:** No person details is displayed. Message indicating that the person index is invalid. <br><br>

3. **Invalid format**

   1. **Test case:** `view 0` <br>
      **Expected:** No person details is displayed. Message indicating invalid command format as 0 is not a positive integer. <br><br>
   2. **Test case:** `view a` <br>
      **Expected:** No person details is displayed. Message indicating invalid command format as 'a' is not a positive integer but a string. <br><br>


[//]: # (@@author felixchanyy)
### Matching Sellers to a Buyer

**Prerequisites:** 
- List all persons using the `list` command. Multiple persons in the list.
- There is a buyer by the name of "James Cook".
- There is a seller by the name of "John Felix".
- There is no seller by the name of "Ben Chan".

1. **Matching suitable sellers to a buyer using buyer's full name**

   1. **Test case:** `matchBuyer James Cook` <br>
      **Expected:** List of sellers who have houses' price less than or equal to the buyer's budget and match the buyer's preferred housing type. <br><br>

1. **Invalid name format**

   1. **Test case:** `matchBuyer James` <br>
      **Expected:** Message indicating invalid format. The specified buyer was not found. <br><br>

   1. **Test case:** `matchBuyer Cook` <br>
      **Expected:** Message indicating invalid format. The specified buyer was not found. <br><br>

1. **Invalid buyer**

   1. **Test case:** `matchBuyer John Felix` <br>
      **Expected:** Message indicating invalid person. The specified person is not a buyer. <br><br>

1. **Buyer does not exist**

    1. **Test case:** `matchBuyer Ben Chan` <br>
       **Expected:** Message indicating invalid person. The specified buyer was not found. <br>

[//]: # (@@author lokidoki102)
### Editing Seller Details

**Prerequisites:**
- There is a seller named "John Felix".
- There is a person named "John Carl 1".
- There is a buyer by the name of "James Cook".
- For each `editSeller` command execution in this manual testing, execute the command `find Felix` 
  to ensure that this person is displayed as the first person in the list.

1. **Successful edit**<br>

    1. **Test case:** `editSeller 1 p/87654321`<br>
       **Expected:** The phone number is edited to "87654321".
       The updated details of the edited seller will also be shown.<br><br>

1. **Duplicate name**<br>

   1. **Test case:** `editSeller 1 n/John Carl 1`<br>
      **Expected:** An error message will be shown, indicating that this person already exists in EstateEase.<br><br>

1. **Invalid person type**<br>

   1. **Test case** `editSeller 1 n/Jessi Yek`<br>
      **Expected:** An error message will be shown, indicating that that person you are 
      trying to edit is not a seller.<br><br>
   
   <box type="info" seamless>
    
    **Note:** Ensure that the first person in the list is a `Buyer`. You can ensure this by executing the command `find James Cook`.
    
   </box>

1. **Invalid INDEX**<br>

   1. **Test case:** `editSeller 0 p/87654321`<br>
      **Expected:** An error messsage will be shown, indicating that the command has invalid format.
      The error message also indicates that the `INDEX` must be a positive number.

<box type="info" seamless>

**Note:** The test cases for invalid input for each parameter is similar the test cases found in `Adding a seller.`

</box>

### Editing Buyer Details

**Prerequisites:**
- Execute the command `list` to show all the person in EstateEase.
- There is a buyer named "James Cook".

1. **Invalid budget value**<br>

    1. **Test case:** `editBuyer 1 budget/-1`<br>
       **Expected:** An error message will be shown, indicating that the budget should be a positive number.<br><br>

    1. **Test case:** `editBuyer 1 budget/0`<br>
       **Expected:** An error message will be shown, indicating that the budget should be a positive number.<br><br>
   
1. **Invalid preferred housing type**<br>

    1. **Test case:** `editBuyer 1 type/bungalow`<br>
       **Expected:** An error message will be shown, indicating that housingType 
        should only be Landed, Hdb or Condominium.<br>

<box type="info" seamless>

**Note:** 
1. The test cases for duplicate names, invalid person type, successful edit, and invalid index are
similar to the test cases found in `Editing Seller Details`.
2. The test cases for invalid input for each parameter is similar the test cases found in `Adding a buyer.`

</box>

[//]: # (@@author)
### Deleting a Person

Prerequisites:
- List all persons using the `list` command. Make sure there are at least 1 person in the list. <br>

1. **Valid index**

    1. **Test case:** `delete 1` <br>
       **Expected:** First person in the displayed list is deleted. <br><br>

2. **Invalid index**

    1. **Test case:** `delete 18` <br>
       **Expected:** No person is deleted in the displayed list. Message indicating that the person index is invalid. <br><br>

3. **Invalid format**

    1. **Test case:** `delete 0` <br>
       **Expected:** No person is deleted in the displayed list. Message indicating invalid command format as 0 is not a positive integer. <br><br>
    2. **Test case:** `delete a` <br>
       **Expected:** No person is deleted in the displayed list. Message indicating invalid command format as 'a' is not a positive integer but a string. <br><br>

[//]: # (@@author KhoonSun47)
### Saving Data
**Prerequisites:**
- Execute a valid command.

1. **Dealing with Missing Data Folder**

    1. **Test case:** Manually delete the `data` folder. <br>
       **Expected:** The application recreates the `data` folder along with a new `addressbook.json` file, saving the current state of data to this new file. <br><br>

2. **Dealing with Missing Data File**

    1. **Test case:** Manually delete the `addressbook.json` file. <br>
       **Expected:** The application recreates the `addressbook.json` file within the existing `data` folder and saves the current state of data to this new file. <br><br>

3. **Dealing with Corrupted Data Files**

    1. **Test case:** The `addressbook.json` is corrupted either by incorrect data or format issues. <br>
       **Expected:** If a valid command is executed after the data file becomes corrupted, the application replaces the corrupted `addressbook.json` with a correctly formatted `addressbook.json` containing the current state of data. If no valid command is executed, the application maintains the corrupted `addressbook.json`. <br><br>

[//]: # (@@author)

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

[//]: # (@@author felixchanyy)
## **Appendix B: Proposed enhancements**

### B.1 New Command: matchSeller

#### B.1.1 Motivation
Real estate agents often need to match sellers with potential buyers efficiently. Currently, our application lacks a feature to facilitate this process. Introducing a `matchSeller` command would enhance the usability of the application for real estate agents by providing a convenient way to find potential buyers who match their property listings.

#### B.1.2 Implementation
1. Implement the `matchSeller` command to filter potential buyers based on seller preferences such as price range and housing type.
2. Integrate the command into the application's existing command structure for seamless user interaction.
3. Display the matched buyers and their relevant details in a clear and organized manner for easy reference.
4. Ensure the command's functionality is efficient and responsive, providing timely results to real estate agents.

### B.2 Limit Price and Budget to 1 Trillion

#### B.2.1 Motivation
In the current implementation, there is no limit on the price and budget fields, which can lead to unrealistic values being entered. Setting a limit of 1 trillion ensures that prices and budgets remain within a reasonable range, preventing errors and maintaining data integrity.

#### B.2.2 Implementation
1. Update the `Price` and `Budget` classes to enforce a maximum value of 1 trillion.
2. Implement validation checks in the user interface to prevent users from entering values exceeding the limit.
3. Provide clear error messages when users attempt to input values beyond the specified limit, guiding them to enter valid data.

This enhancement improves the usability and reliability of the application by ensuring that price and budget inputs are realistic and within acceptable bounds.

### B.3 Price and Budget 2 Decimal Places Only

#### B.3.1 Motivation
Allowing prices and budgets to have more than two decimal places can lead to confusion and inaccuracies. Limiting them to two decimal places ensures consistency and precision in financial calculations.

#### B.3.2 Implementation
1. Modify the `Price` and `Budget` classes to round values to two decimal places during input validation.
2. Update user interface components to accept inputs with up to two decimal places only.
3. Provide feedback to users if they attempt to input values with more than two decimal places, informing them of the restriction and prompting them to correct their input.

This enhancement promotes clarity and accuracy in price and budget management within the application.

[//]: # (@@author)
### B.4 Restrict Landed Properties to a Unique Postal Code

#### B.4.1 Motivation
In the current implementation, HDBs and Condominiums are allowed to share postal codes, similar to real world situations. However, following further research, landed properties have shown no need to share postal codes.

#### B4.2 Implementation
1. Update the method used to check Landed Property uniqueness. Currently, the method uses the whole string. Include postal code uniqueness as a requirement.

### B.5 Phone Number Field

#### B.5.1 Motivation
- In the current implementation, the `phone` field accepts more than three digits without specifically limiting the input to the standard eight digits customary for Singaporean phone numbers, despite the application being Singapore-focused.
- This design decision accounts for the potential users living abroad with international `phone` numbers, such as Singaporeans residing overseas who wish to purchase property back home, or foreigners intending to relocate to Singapore who may not yet have a local `phone` number.
- However, this method has led to confusion, since it permits the entry of invalid `phone` numbers into the system due to the absence of strict validation criteria.

#### B.5.2 Implementation
- To enhance the system's flexibility while maintaining data integrity, one potential improvement could involve updating our validation strategy,which is to introduce a validation mechanism that recognizes and accommodates both local (8-digit) and international `phone` number formats. This could involve specifying a more complex regex pattern or implementing a logic that checks for a country code prefix to distinguish between local and international numbers.

[//]: # (@@author redcolorbicycle)
### B.6 Edit House Command

#### B.6.1 Motivation
- In the current implementation, the `editHouse` command was not implemented as it could be broken down into `deleteHouse` and `addHouse` and was not seen as necessary.
- However, to increase user convenience, `editHouse` can be implemented in future versions.

#### B.6.2 Implementation
- Similar to the current `addHouse` and `deleteHouse` commands, `editHouse` would require seller and the exact house details. The logic would be fundamentally the same.

### B.7 Switch all Name-Based Commands to Index-Based Commands

#### B.7.1 Motivation
- Currently, commands like `addHouse`, `deleteHouse`, and `matchBuyer` utilize name-based identifiers, whereas other operations are index-based.
- To standardize all commands and minimize user errors (e.g., typos in long names), it has been decided to switch all commands to index-based commands. This change will improve accuracy and streamline user interactions.

#### B.7.2 Implementation
- The implementation will involve modifying existing name-based commands to use `index`, similar to how `editSeller`, `editBuyer` and `view` are currently structured. This will ensure consistency across the application, making it easier for users to interact with EstateEase.

[//]: # (@@author KhoonSun47)
### B.8 Unique Identifier Refinement

#### B.8.1 Motivation
- The commands `addSeller`, `addBuyer`, `editSeller` and `editBuyer` currently validates whether a `person` exist in the application by checking the uniqueness of the `name`, which may lead to confusion when multiple `person` could share the same `name`. 
- Even though alphanumeric entries (e.g., `John Doe 1`, `John Doe 2`) are allowed to identify different `person` with the same `name`, this method has proven to be inconvenient and potentially confusing for user managing multiple entries with similar or identical `name`.

#### B.8.2 Implementation
- Moving forward, while `name` can be non-unique, each `person` in the `Person` list will be uniquely identified by two mandatory fields: `email` and `phone`.
- If a new `seller` or `buyer` is added with an `email` or `phone` that matches an existing entry, the system will reject the addition to prevent duplicates. Similarly, modifications to `email` or `phone` in commands like `editSeller` or `editBuyer` will be checked against existing entries, and duplicates will not be allowed.
- This enhancement ensures the uniqueness of each `person` in EstateEase, as both `email` and `phone` are unique to each `person`, thereby reducing confusion to the user.

[//]: # (@@author zengzihui)
### B.9 Display Person Details after AddHouse & DeleteHouse Command

#### B.9.1 Motivation
- In the current implementation, after the user adds a house to a seller or deletes a house from a seller, the list being displayed is the person list. Consequently, the user can only check for updated house details by viewing the selected seller. This can be troublesome for users.

#### B.9.2 Implementation
- To enhance the user experience, one potential improvement could be displaying the seller's personal and house details after an "add house" or "delete house" command is performed.
