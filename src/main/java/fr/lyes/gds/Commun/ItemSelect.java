package fr.lyes.gds.Commun;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemSelect {
	@Getter
	@Setter
	private Long value;
	@Getter
	@Setter
	private String label;

	@Override
	public String toString() {
		return "ItemSelect [value=" + value + ", label=" + label + "]";
	}

}
