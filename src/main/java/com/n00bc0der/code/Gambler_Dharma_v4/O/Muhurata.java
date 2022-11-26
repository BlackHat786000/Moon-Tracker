package com.n00bc0der.code.Gambler_Dharma_v4.O;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

public class Muhurata {

	static int ASC;

	static int DSC;

	public static int Calculate_Muhurata_ASC(Multimap<Integer, String> house_view, String csl) {

		ASC = 0;

		for (int i = 1; i <= 12; i++) {

			if ((i == 1 || i == 2 || i == 3 || i == 6 || i == 10 || i == 11)
					&& ((Iterables.get(house_view.get(i), 0).equals(""))
							&& (Iterables.get(house_view.get(i), 1).equals(""))
							&& (Iterables.get(house_view.get(i), 2).equals(""))
							&& (Iterables.get(house_view.get(i), 3).contains(csl)))) {
				ASC = ASC + 4;
			} else if ((i == 7 || i == 8 || i == 9 || i == 4 || i == 5 || i == 12)
					&& ((Iterables.get(house_view.get(i), 0).equals(""))
							&& (Iterables.get(house_view.get(i), 1).equals(""))
							&& (Iterables.get(house_view.get(i), 2).equals(""))
							&& (Iterables.get(house_view.get(i), 3).contains(csl)))) {
				ASC = ASC - 4;
			} else if ((i == 1 || i == 2 || i == 3 || i == 6 || i == 10 || i == 11)
					&& ((Iterables.get(house_view.get(i), 0).equals(""))
							&& (Iterables.get(house_view.get(i), 1).equals("")))) {
				if (Iterables.get(house_view.get(i), 2).contains(csl)) {
					ASC = ASC + 4;
				} else if (Iterables.get(house_view.get(i), 3).contains(csl)) {
					ASC = ASC + 2;
				}
			} else if ((i == 7 || i == 8 || i == 9 || i == 4 || i == 5 || i == 12)
					&& ((Iterables.get(house_view.get(i), 0).equals(""))
							&& (Iterables.get(house_view.get(i), 1).equals("")))) {
				if (Iterables.get(house_view.get(i), 2).contains(csl)) {
					ASC = ASC - 4;
				} else if (Iterables.get(house_view.get(i), 3).contains(csl)) {
					ASC = ASC - 2;
				}
			} else {
				int cnt = 4;
				for (int j = 0; j <= 3; j++) {
					String s = Iterables.get(house_view.get(i), j);

					if ((i == 1 || i == 2 || i == 3 || i == 6 || i == 10 || i == 11) && (s.contains(csl))) {
						ASC = ASC + cnt;
					} else if ((i == 7 || i == 8 || i == 9 || i == 4 || i == 5 || i == 12) && (s.contains(csl))) {
						ASC = ASC - cnt;
					}
					cnt = cnt - 1;
				}
			}

		}

		return ASC;

	}

	public static int Calculate_Muhurata_DSC(Multimap<Integer, String> house_view, String csl) {

		DSC = 0;

		for (int i = 1; i <= 12; i++) {

			if ((i == 1 || i == 2 || i == 3 || i == 6 || i == 10 || i == 11)
					&& ((Iterables.get(house_view.get(i), 0) == "") && (Iterables.get(house_view.get(i), 1) == "")
							&& (Iterables.get(house_view.get(i), 2) == ""
									&& (Iterables.get(house_view.get(i), 3).contains(csl))))) {
				DSC = DSC - 4;
			} else if ((i == 7 || i == 8 || i == 9 || i == 4 || i == 5 || i == 12)
					&& ((Iterables.get(house_view.get(i), 0) == "") && (Iterables.get(house_view.get(i), 1) == "")
							&& (Iterables.get(house_view.get(i), 2) == ""
									&& (Iterables.get(house_view.get(i), 3).contains(csl))))) {
				DSC = DSC + 4;
			} else if ((i == 1 || i == 2 || i == 3 || i == 6 || i == 10 || i == 11)
					&& ((Iterables.get(house_view.get(i), 0) == "") && (Iterables.get(house_view.get(i), 1) == ""))) {
				if (Iterables.get(house_view.get(i), 2).contains(csl)) {
					DSC = DSC - 4;
				} else if (Iterables.get(house_view.get(i), 3).contains(csl)) {
					DSC = DSC - 2;
				}
			} else if ((i == 7 || i == 8 || i == 9 || i == 4 || i == 5 || i == 12)
					&& ((Iterables.get(house_view.get(i), 0) == "") && (Iterables.get(house_view.get(i), 1) == ""))) {
				if (Iterables.get(house_view.get(i), 2).contains(csl)) {
					DSC = DSC + 4;
				} else if (Iterables.get(house_view.get(i), 3).contains(csl)) {
					DSC = DSC + 2;
				}
			} else {
				int cnt = 4;
				for (int j = 0; j <= 3; j++) {
					String s = Iterables.get(house_view.get(i), j);

					if ((i == 1 || i == 2 || i == 3 || i == 6 || i == 10 || i == 11) && (s.contains(csl))) {
						DSC = DSC - cnt;
					} else if ((i == 7 || i == 8 || i == 9 || i == 4 || i == 5 || i == 12) && (s.contains(csl))) {
						DSC = DSC + cnt;
					}
					cnt = cnt - 1;
				}
			}

		}

		return DSC;

	}

}
