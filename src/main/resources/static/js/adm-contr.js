document.addEventListener('DOMContentLoaded', () => {

 		// Fetch users and populate main table
 		async function fetchUsers() {
 			try {
 				const response = await fetch('/api/users');
 				if (!response.ok) {
 					throw new Error(`Error: ${response.status}`);
 				}
 				const users = await response.json();
 				populateTable(users);
 			} catch (error) {
 				console.error('Error while trying to fetch users:', error);
 			}
 		}

 		function populateTable(users) {
 			const userTableBody = document.getElementById('userTableBody');
 			userTableBody.innerHTML = '';
 			users.forEach(user => {
 				const row = document.createElement('tr');

 				row.innerHTML = `
 					<td>${user.id}</td>
 					<td>${user.firstName}</td>
 					<td>${user.lastName}</td>
 					<td>${user.age}</td>
 					<td>${user.email}</td>
 					<td>${formatUserRoles(user.roles)}</td>
 					<td><button class="btn btn-success btn-sm btn-edit" data-bs-toggle="modal" data-bs-target="#editUserModal">Edit</button></td>
 					<td><button class="btn btn-delete btn-sm btn-delete-modal" data-bs-toggle="modal" data-bs-target="#deleteUserModal">Delete</button></td>
 				`;
 				userTableBody.appendChild(row);
 			});
 		}

 		function formatUserRoles(roles) {
 			return roles.map(role => role.name).join(' ');
 		}

        fetchUsers();



        // Put a list of roles to Add Edit Delete forms
        async function fetchRolesAndPopulateForms() {
            try {
                const response = await fetch("/api/users/roles", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }
                const roles = await response.json();

                const selectIds = ["deleteRole", "editRole", "roles"];

                selectIds.forEach(selectId => {
                    const selectElement = document.getElementById(selectId);

                    roles.forEach(role => {
                        const option = document.createElement("option");
                        option.value = role.name;
                        option.textContent = role.name;
                        selectElement.appendChild(option);
                    });
                });
            } catch (error) {
                console.error("Error fetching roles:", error);
            }
        }

        fetchRolesAndPopulateForms();



        // Table buttons handlers
        document.getElementById('userTableBody').addEventListener('click', (e) => {

            if (e.target.classList.contains('btn-edit')) {
                const row = e.target.closest('tr');
                const id = row.cells[0].textContent;
                const firstName = row.cells[1].textContent;
                const lastName = row.cells[2].textContent;
                const age = row.cells[3].textContent;
                const email = row.cells[4].textContent;
                const roles = row.cells[5].textContent.split(' ');

                document.getElementById('editId').value = id;
                document.getElementById('editFirstName').value = firstName;
                document.getElementById('editLastName').value = lastName;
                document.getElementById('editAge').value = age;
                document.getElementById('editEmail').value = email;

                const roleSelect = document.getElementById('editRole');
                Array.from(roleSelect.options).forEach(option => {
                    option.selected = roles.includes(option.value);
                });
			}

			if (e.target.classList.contains('btn-delete-modal')) {
                const row = e.target.closest('tr');
                const id = row.cells[0].textContent;
                const firstName = row.cells[1].textContent;
                const lastName = row.cells[2].textContent;
                const age = row.cells[3].textContent;
                const email = row.cells[4].textContent;
                const roles = row.cells[5].textContent.split(' ');

                document.getElementById('deleteId').value = id;
                document.getElementById('deleteFirstName').value = firstName;
                document.getElementById('deleteLastName').value = lastName;
                document.getElementById('deleteAge').value = age;
                document.getElementById('deleteEmail').value = email;

                 const roleSelect = document.getElementById('deleteRole');
                 Array.from(roleSelect.options).forEach(option => {
                    option.selected = roles.includes(option.value);
                 });
            }
        });



		// Forms handlers
		const addUserForm = document.getElementById('addUserForm');
        const userTableTab = document.getElementById('users-table-tab');

		const editUserForm = document.getElementById('editUserForm');
        const editUserModal = new bootstrap.Modal(document.getElementById('editUserModal'));

		const deleteUserForm = document.getElementById('deleteUserForm');
        const deleteUserModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));

		// Add
        addUserForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const firstName = document.getElementById('firstName').value;
            const lastName = document.getElementById('lastName').value;
            const age = document.getElementById('age').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            const rolesSelect = document.getElementById('roles');
            const roles = Array.from(rolesSelect.selectedOptions).map(option => ({ name: option.value }));

            const newUser = { firstName, lastName, age, email, password, roles };

            try {
                const response = await fetch('/api/users', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(newUser),
                });
                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }
                addUserForm.reset();
				fetchUsers();
                const bootstrapTab = new bootstrap.Tab(userTableTab);
                bootstrapTab.show();
            } catch (error) {
                console.error('Error while trying to add user:', error);
            }
        });

		// Edit
        editUserForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const id = document.getElementById('editId').value;
            const firstName = document.getElementById('editFirstName').value;
            const lastName = document.getElementById('editLastName').value;
            const age = document.getElementById('editAge').value;
            const email = document.getElementById('editEmail').value;
            const password = document.getElementById('editPassword').value;

            const rolesSelect = document.getElementById('editRole');
            const roles = Array.from(rolesSelect.selectedOptions).map(option => ({ name: option.value }));

            const user = { firstName, lastName, age, email, password, roles };

            try {
                const response = await fetch(`/api/users/${id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(user),
                });
                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }
                fetchUsers();
                editUserModal.hide();
            } catch (error) {
                console.error('Error while trying to edit user:', error);
            }
        });

        // Delete
        deleteUserForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const id = document.getElementById('deleteId').value;

            try {
                const response = await fetch(`/api/users/${id}`, {
                    method: 'DELETE',
                });
                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }
                fetchUsers();
                deleteUserModal.hide();
            } catch (error) {
                console.error('Error while trying to delete user:', error);
            }
        });

    })