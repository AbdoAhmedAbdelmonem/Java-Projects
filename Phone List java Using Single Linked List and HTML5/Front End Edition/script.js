let contacts = [];

function showAddContact() {
    hideForms();
    document.getElementById("addContact").style.display = "block";
}

function showSearchContact() {
    hideForms();
    document.getElementById("searchContact").style.display = "block";
}

function showDeleteContact() {
    hideForms();
    document.getElementById("deleteContact").style.display = "block";
}

function hideForms() {
    document.getElementById("addContact").style.display = "none";
    document.getElementById("searchContact").style.display = "none";
    document.getElementById("deleteContact").style.display = "none";
}

function addContact() {
    const name = document.getElementById("contactName").value;
    const phone = document.getElementById("contactPhone").value;
    if (name === "" || phone === "") {
        alert("Please enter both name and phone number.");
        return;
    }

    if (contacts.some(contact => contact.name === name)) {
        alert("Contact with this name already exists.");
        return;
    }

    contacts.push({ name: name, phone: phone });
    alert("Contact added successfully.");
    hideForms();
    displayContacts();
    document.getElementById('addContact').classList.remove('hidden');
}

function displayContacts() {
    const contactList = document.getElementById("contacts");
    contactList.innerHTML = "";
    if (contacts.length === 0) {
        contactList.innerHTML = "<li>Empty List</li>";
    } else {
        contacts.forEach(contact => {
            const li = document.createElement("li");
            li.textContent = `[Name: ${contact.name}], [Phone: ${contact.phone}]`;
            contactList.appendChild(li);
        });
    }
}

function searchContact() {
    const name = document.getElementById("searchName").value;
    const contact = contacts.find(contact => contact.name === name);
    if (contact) {
        alert(`Contact found: Name: ${contact.name}, Phone: ${contact.phone}`);
    } else {
        alert("There is no such contact with this name.");
    }
    hideForms();
    document.getElementById('searchContact').classList.remove('hidden');
}

function deleteContact() {
    const name = document.getElementById("deleteName").value;
    const index = contacts.findIndex(contact => contact.name === name);
    if (index !== -1) {
        contacts.splice(index, 1);
        alert("Contact removed successfully.");
        displayContacts();
    } else {
        alert("Contact not found.");
    }
    hideForms();
}
document.getElementById("displayButton").addEventListener("click", function () {
    const contactList = document.getElementById("contactList");
    if (contactList.classList.contains("hidden")) {
        contactList.classList.remove("hidden");
    } else {
        contactList.classList.add("hidden");
    }
});


