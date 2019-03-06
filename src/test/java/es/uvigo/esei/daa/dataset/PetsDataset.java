package es.uvigo.esei.daa.dataset;

import es.uvigo.esei.daa.entities.Pet;

import static java.util.Arrays.stream;

public final class PetsDataset {
	private PetsDataset() {}
	
	public static Pet[] pets() {
		return new Pet[] {
			new Pet(1, 1, "Tom"),
			new Pet(2, 1, "Jerry")
		};
	}


	public static Pet pet(int id) {
		return stream(pets())
				.filter(p -> p.getId() == id)
				.findAny()
				.orElseThrow(IllegalArgumentException::new);
	}
	public static int existentId() {
		return 1;
	}

	public static Pet existentPet() {
		return pet(existentId());
	}
}
