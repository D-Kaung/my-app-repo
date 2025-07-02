
    $(document).ready(function () {
    $('#townshipTable').DataTable({
        "pageLength": 5,
        "ordering" : false,
        "lengthMenu": [5, 10, 25, 50],
        "language": {
            "search": "Search:",
            "lengthMenu": "Show _MENU_ entries",
            "info": "Showing _START_ to _END_ of _TOTAL_ entries"
        }
    });
});
