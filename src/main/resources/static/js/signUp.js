
    document.addEventListener("DOMContentLoaded", function () {
    const divisionSelect = document.getElementById("division");
    const townshipSelect = document.getElementById("township");
    const hiddenInput = document.getElementById("township-id-hidden");
    const addressField = document.getElementById("address");

    divisionSelect.addEventListener("change", async function () {
    const divisionId = this.value;
    townshipSelect.innerHTML = '<option value="">Loading...</option>';
    hiddenInput.value = "";
    updateAddress(); // Clear address when division changes

    if (!divisionId) {
    townshipSelect.innerHTML = '<option value="">Select Division First</option>';
    return;
}

    try {
    const response = await fetch(`/townships?divisionId=${divisionId}`);
    if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

    const townships = await response.json();
    console.log("Received townships:", townships);

    townshipSelect.innerHTML = '<option value="">Select Township</option>';
    townships.forEach(township => {
    const option = new Option(township.name, township.id);
    townshipSelect.add(option);
});

} catch (error) {
    console.error("Error fetching townships:", error);
    townshipSelect.innerHTML = '<option value="">Error loading townships</option>';
}
});

    townshipSelect.addEventListener("change", function () {
    hiddenInput.value = this.value;
    updateAddress(); // Update address when township changes
});

    function updateAddress() {
    const selectedDivision = divisionSelect.options[divisionSelect.selectedIndex].text;
    const selectedTownship = townshipSelect.options[townshipSelect.selectedIndex].text;

    // Only update if both are selected
    if (divisionSelect.value && townshipSelect.value) {
    addressField.value = `${selectedDivision}, ${selectedTownship}`;
} else {
    addressField.value = "";
}
}

    // Initialize if division is preselected
    if (divisionSelect.value) {
    divisionSelect.dispatchEvent(new Event("change"));
}
});

    // Update your date validation to ensure proper format
    document.getElementById("dateOfBirth").addEventListener("change", function () {
    if (!this.value) return;

    const dob = new Date(this.value);
    const today = new Date();

    // Validate minimum age (13 years)
    let age = today.getFullYear() - dob.getFullYear();
    const monthDiff = today.getMonth() - dob.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < dob.getDate())) {
    age--;
}

    if (age < 13) {
    alert("You must be at least 13 years old");
    this.value = "";
}
});


    // Load the Google Maps API asynchronously
    function loadGoogleMapsApi(callback) {
    const existingScript = document.getElementById('googleMaps');

    if (!existingScript) {
    const script = document.createElement('script');
    script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyBN93kkmCC5LXfQZ-lHndlxO5lOSgRZu_8&callback=${callback.name}`;
    script.id = 'googleMaps';
    script.async = true;
    script.defer = true;
    document.body.appendChild(script);
} else {
    callback();
}
}

    // This function initializes the map and marker and syncs with inputs
    function initMap() {
    const latInput = document.getElementById('latitude');
    const lngInput = document.getElementById('longitude');
    const mapDiv = document.getElementById('map');

    // Default position (can be your region center)
    const defaultPosition = {lat: 16.84556457830302, lng: 96.1216427973765};

    const map = new google.maps.Map(mapDiv, {
    center: defaultPosition,
    zoom: 12,
});

    // Create a draggable marker
    const marker = new google.maps.Marker({
    position: defaultPosition,
    map: map,
    draggable: true,
    title: "Drag me or click on map",
});

    // When the marker is dragged, update the input fields
    marker.addListener('position_changed', () => {
    const pos = marker.getPosition();
    latInput.value = pos.lat().toFixed(6);
    lngInput.value = pos.lng().toFixed(6);
});

    // When the map is clicked, move the marker and update inputs
    map.addListener('click', (event) => {
    marker.setPosition(event.latLng);
    latInput.value = event.latLng.lat().toFixed(6);
    lngInput.value = event.latLng.lng().toFixed(6);
});

    // When user types in inputs, move the marker accordingly
    function updateMarkerFromInputs() {
    const lat = parseFloat(latInput.value);
    const lng = parseFloat(lngInput.value);

    if (!isNaN(lat) && !isNaN(lng)) {
    const newPos = new google.maps.LatLng(lat, lng);
    marker.setPosition(newPos);
    map.panTo(newPos);
}
}

    latInput.addEventListener('change', updateMarkerFromInputs);
    lngInput.addEventListener('change', updateMarkerFromInputs);

    // Initialize inputs with default position
    latInput.value = defaultPosition.lat.toFixed(6);
    lngInput.value = defaultPosition.lng.toFixed(6);
}

    // Load Google Maps API and initialize the map
    loadGoogleMapsApi(initMap);
