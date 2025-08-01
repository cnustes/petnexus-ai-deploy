\# PetNexus AI - User Stories



\## Story 1: View Upcoming Appointments



\### Persona

As a registered Pet Owner.



\### Story

I want to view a list of my upcoming appointments for all my pets.



\### Benefit

So that I can keep track of my pet's health schedule and plan my personal calendar accordingly.



\### Acceptance Criteria

\- Given I am a logged-in user, when I request my appointments, the system must verify my identity.

\- If I am not authenticated, the API must return a `401 Unauthorized` error.

\- When I make a successful request, the API must return a `200 OK` status with a list of my future appointments.

\- Each appointment in the list must contain the `appointmentId`, `petName`, `appointmentDate`, `reasonForVisit`, and `clinicName`.

\- If I have no upcoming appointments, the API should return a `200 OK` status with an empty list.

\- The API must not show appointments that have already passed.



\### Mapped Endpoint

`GET /api/appointments/upcoming`



---



\## Story 2: Book a New Appointment



\### Persona

As a registered Pet Owner.



\### Story

I want to book a new appointment for my pet for a specific service (e.g., vaccination, check-up).



\### Benefit

So that I can conveniently schedule necessary care for my pet without having to call the clinic.



\### Acceptance Criteria

\- Given I am a logged-in user, I must provide my pet's ID, a future date/time, and a reason for the visit.

\- The API must validate that the requested time slot is available; if not, it should return a `409 Conflict` error.

\- Upon successful booking, the API must return a `201 Created` status with the details of the new appointment.

\- If any required information is missing or invalid (e.g., a date in the past), the API must return a `400 Bad Request` error.



\### Mapped Endpoint

`POST /api/appointments`



---



\## Story 3: View Pet Vaccination History



\### Persona

As a registered Pet Owner.



\### Story

I want to view the complete vaccination history for one of my pets.



\### Benefit

So that I can ensure my pet's vaccinations are up-to-date and provide this information for kennels, travel, or groomers.



\### Acceptance Criteria

\- Given I am a logged-in user, I can request the vaccination record by providing a `petId`.

\- The API must verify that the `petId` belongs to the authenticated user.

\- Upon successful request, the API returns `200 OK` with a list of vaccinations, each including `vaccineName` and `dateAdministered`.

\- If the `petId` is invalid or does not belong to me, the API must return a `404 Not Found` error.



\### Mapped Endpoint

`GET /api/pets/{petId}/vaccination-record`



---



\## Story 4: User Registration



\### Persona

As a new visitor to the platform.



\### Story

I want to create a new user account using my email and a password.



\### Benefit

So that I can gain access to the platform and start managing my pet's health information.



\### Acceptance Criteria

\- The registration endpoint must be public.

\- I must provide a valid email, a password meeting the security criteria, and my name.

\- The system must check if the email is already registered. If so, it must return a `409 Conflict` error.

\- Upon successful registration, the system creates the user account and returns a `201 Created` status.



\### Mapped Endpoint

\[cite\_start]`POST /api/users/register` \[cite: 17]

