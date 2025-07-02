    $(document).ready(function () {
    $('#townshipTable').DataTable({
        "ordering": false,
        pageLength: 5,
        lengthMenu: [5, 10, 25, 50],
        language: {
            search: "Search:",
            lengthMenu: "Show _MENU_ entries",
            info: "Showing _START_ to _END_ of _TOTAL_ entries",
            paginate: {
                previous: "Previous",
                next: "Next"
            }
        },
        // Responsive layout if you want to add:
        responsive: true,

    });
});
