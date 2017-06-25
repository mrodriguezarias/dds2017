package tp1.model.indicator;

import tp1.model.Company;

@FunctionalInterface
public interface Expression {
	double eval(Company company, short period);
}
