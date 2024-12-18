document.addEventListener('DOMContentLoaded', () => {

    // Fetch details of authorized user
    fetch("/api/users/self", {
        method: "GET",
        headers: {
        "Content-Type": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Put email and roles into header
        document.getElementById("userEmail").textContent = data.email;
        document.getElementById("userRoles").textContent = data.roles;

        // Put user details table if it's /api/user page
        if (document.getElementById('principalTableBody')) {

            const principalTableBody = document.getElementById('principalTableBody');
            principalTableBody.innerHTML = '';

            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${data.id}</td>
                <td>${data.firstName}</td>
                <td>${data.lastName}</td>
                <td>${data.age}</td>
                <td>${data.email}</td>
                <td>${data.roles}</td>
            `;
            principalTableBody.appendChild(row);
        }
    })
    .catch(error => {
        console.error("Error fetching user details:", error);
    });

})