import React, { useState, useEffect } from 'react';
import Modal from 'react-modal';
import Select from 'react-select';
import { createPet, getSpecies, getBreedsBySpeciesId } from '../services/petService';

const customStyles = {
  content: {
    top: '50%', left: '50%', right: 'auto', bottom: 'auto',
    marginRight: '-50%', transform: 'translate(-50%, -50%)',
    backgroundColor: '#40414F', border: '1px solid #565869',
    borderRadius: '8px', width: '90%', maxWidth: '500px'
  },
  overlay: { backgroundColor: 'rgba(0, 0, 0, 0.75)' }
};

Modal.setAppElement('#root');

function AddPetModal({ isOpen, onRequestClose, onPetAdded }) {
  const [name, setName] = useState('');
  const [birthDate, setBirthDate] = useState('');
  const [error, setError] = useState('');
  const [speciesOptions, setSpeciesOptions] = useState([]);
  const [breedOptions, setBreedOptions] = useState([]);
  const [selectedSpecies, setSelectedSpecies] = useState(null);
  const [selectedBreed, setSelectedBreed] = useState(null);
  const [isBreedsLoading, setIsBreedsLoading] = useState(false);

  useEffect(() => {
    if (isOpen) {
      getSpecies().then(data => {
        const options = data.map(s => ({ value: s.id, label: s.name }));
        setSpeciesOptions(options);
      });
    } else {
      setName('');
      setBirthDate('');
      setSelectedSpecies(null);
      setSelectedBreed(null);
      setBreedOptions([]);
      setError('');
    }
  }, [isOpen]);

  useEffect(() => {
    if (selectedSpecies) {
      setIsBreedsLoading(true);
      setBreedOptions([]);
      setSelectedBreed(null);
      getBreedsBySpeciesId(selectedSpecies.value).then(data => {
        const options = data.map(b => ({ value: b.id, label: b.name }));
        setBreedOptions(options);
        setIsBreedsLoading(false);
      });
    }
  }, [selectedSpecies]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!selectedSpecies || !selectedBreed) {
        setError("Please select a species and a breed.");
        return;
    }
    setError('');
    try {
      const newPet = { name, birthDate, speciesName: selectedSpecies.label, breedName: selectedBreed.label };
      await createPet(newPet);
      onPetAdded();
    } catch (err) {
      setError('Failed to add pet. Please try again.');
    }
  };

  const selectStyles = {
    control: (styles) => ({ ...styles, backgroundColor: '#565869', border: 'none', boxShadow: 'none' }),
    option: (styles, { isFocused, isSelected }) => ({ ...styles, backgroundColor: isSelected ? '#2A85FF' : isFocused ? '#444654' : '#565869', color: '#ECECEC', ':active': { ...styles[':active'], backgroundColor: '#2A85FF' } }),
    singleValue: (styles) => ({ ...styles, color: '#ECECEC' }),
    input: (styles) => ({ ...styles, color: '#ECECEC' }),
    menu: (styles) => ({ ...styles, backgroundColor: '#565869' }),
  };

  return (
    <Modal isOpen={isOpen} onRequestClose={onRequestClose} style={customStyles}>
      <form onSubmit={handleSubmit} className="auth-form">
        <h2>Add a New Pet</h2>

        {/* --- RESTORED FORM FIELDS --- */}
        <div className="form-group">
          <input id="name" type="text" value={name} onChange={(e) => setName(e.target.value)} required placeholder=" " />
          <label htmlFor="name">Pet's Name</label>
        </div>

        <div className="form-group">
          <label className="select-label">Species</label>
          <Select options={speciesOptions} value={selectedSpecies} onChange={setSelectedSpecies} styles={selectStyles} placeholder="Select a species..." />
        </div>

        <div className="form-group">
          <label className="select-label">Breed</label>
          <Select options={breedOptions} value={selectedBreed} onChange={setSelectedBreed} styles={selectStyles} placeholder="Select a breed..." isDisabled={!selectedSpecies || isBreedsLoading} isLoading={isBreedsLoading} />
        </div>

        <div className="form-group">
          <label htmlFor="birthDate" className="date-label">Birth Date</label>
          <input id="birthDate" type="date" value={birthDate} onChange={(e) => setBirthDate(e.target.value)} />
        </div>

        <button type="submit">Save Pet</button>
        {error && <p className="error-message">{error}</p>}
      </form>
    </Modal>
  );
}

export default AddPetModal;