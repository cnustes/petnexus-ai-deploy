import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getPets } from '../services/petService';
import { ClipLoader } from 'react-spinners';
import AddPetModal from '../components/AddPetModal';

function PetsPage() {
  const [pets, setPets] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const fetchPets = async () => {
    setIsLoading(true);
    try {
      const userPets = await getPets();
      setPets(userPets);
    } catch (err) {
      setError('Failed to load pets. Please try again later.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchPets();
  }, []);

  const handlePetAdded = () => {
    setIsModalOpen(false);
    fetchPets();
  };

  if (error) {
    return <div className="page-container"><h2>{error}</h2></div>;
  }

  return (
    <>
      <div className="page-container">
        <div className="pets-dashboard">
          <h1>My Pets</h1>
          {isLoading ? (
            <ClipLoader color="#fff" size={50} />
          ) : (
            <>
              <div className="pets-list">
                {pets.length > 0 ? (
                  pets.map(pet => (
                    <Link to={`/pets/${pet.id}`} key={pet.id} className="pet-card">
                      <h3>{pet.name}</h3>
                      {/* --- THIS LINE IS CORRECTED --- */}
                      <p>{pet.speciesName} - {pet.breedName}</p>
                    </Link>
                  ))
                ) : (
                  <p>You haven't added any pets yet. Add one to get started!</p>
                )}
              </div>
              <button onClick={() => setIsModalOpen(true)} className="add-pet-button">+ Add New Pet</button>
            </>
          )}
        </div>
      </div>

      <AddPetModal
        isOpen={isModalOpen}
        onRequestClose={() => setIsModalOpen(false)}
        onPetAdded={handlePetAdded}
      />
    </>
  );
}

export default PetsPage;