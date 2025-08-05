import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL;

/**
 * Gets the auth token from localStorage.
 */
const getAuthHeaders = () => {
    const token = localStorage.getItem('jwt_token');
    if (!token) throw new Error("No auth token found.");
    return { 'Authorization': `Bearer ${token}` };
};

/**
 * Fetches all pets for the currently authenticated user.
 */
export const getPets = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/pets`, { headers: getAuthHeaders() });
        return response.data;
    } catch (error) {
        console.error("Failed to fetch pets:", error);
        throw error;
    }
};

/**
 * Creates a new pet for the authenticated user.
 */
export const createPet = async (petData) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/api/pets`, petData, { headers: getAuthHeaders() });
        return response.data;
    } catch (error) {
        console.error("Failed to create pet:", error);
        throw error;
    }
};

/**
 * Fetches all available species. This is a public endpoint.
 */
export const getSpecies = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/species`);
        return response.data;
    } catch (error) {
        console.error("Failed to fetch species:", error);
        throw error;
    }
};

/**
 * Fetches all breeds for a given species ID. This is a public endpoint.
 */
export const getBreedsBySpeciesId = async (speciesId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/species/${speciesId}/breeds`);
        return response.data;
    } catch (error) {
        console.error(`Failed to fetch breeds for species ID ${speciesId}:`, error);
        throw error;
    }
};