
public class Tax {

	public static double calculateTax(double total, State state) {
		//fake rates
		switch (state) {
			case AL: return total * 0.01;
			case AK: return total * 0.02;
			case AZ: return total * 0.03;
			case AR: return total * 0.04;
			case CA: return total * 0.05;
			case CO: return total * 0.06;
			case CT: return total * 0.07;
			case DE: return total * 0.08;
			case FL: return total * 0.09;
			case GA: return total * 0.10;
			case HI: return total * 0.11;
			case ID: return total * 0.12;
			case IL: return total * 0.13;
			case IN: return total * 0.14;
			case IA: return total * 0.15;
			case KS: return total * 0.16;
			case KY: return total * 0.17;
			case LA: return total * 0.18;
			case ME: return total * 0.19;
			case MD: return total * 0.20;
			case MA: return total * 0.21;
			case MI: return total * 0.22;
			case MN: return total * 0.23;
			case MS: return total * 0.24;
			case MO: return total * 0.25;
			case MT: return total * 0.26;
			case NE: return total * 0.27;
			case NV: return total * 0.28;
			case NH: return total * 0.29;
			case NJ: return total * 0.30;
			case NM: return total * 0.31;
			case NY: return total * 0.32;
			case NC: return total * 0.33;
			case ND: return total * 0.34;
			case OH: return total * 0.35;
			case OK: return total * 0.36;
			case OR: return total * 0.37;
			case PA: return total * 0.38;
			case RI: return total * 0.39;
			case SC: return total * 0.40;
			case SD: return total * 0.41;
			case TN: return total * 0.42;
			case TX: return total * 0.43;
			case UT: return total * 0.44;
			case VT: return total * 0.45;
			case VA: return total * 0.46;
			case WA: return total * 0.47;
			case WV: return total * 0.48;
			case WI: return total * 0.49;
			case WY: return total * 0.50;
		default:
			break; 
		}
		return -1;
	}

}
