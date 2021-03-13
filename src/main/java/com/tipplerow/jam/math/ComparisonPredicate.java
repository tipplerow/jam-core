
package com.tipplerow.jam.math;

import java.util.function.DoublePredicate;

abstract class ComparisonPredicate implements DoublePredicate {
    protected final double bound;
    protected final DoubleComparator comparator;

    protected ComparisonPredicate(double bound) {
        this(bound, DoubleComparator.DEFAULT);
    }

    protected ComparisonPredicate(double bound, double tolerance) {
        this(bound, new DoubleComparator(tolerance));
    }

    protected ComparisonPredicate(double bound, DoubleComparator comparator) {
        this.bound = bound;
        this.comparator = comparator;
    }

    static DoublePredicate LT(double bound) {
        return new PredicateLT(bound);
    }

    static DoublePredicate LE(double bound) {
        return new PredicateLE(bound);
    }

    static DoublePredicate GE(double bound) {
        return new PredicateGE(bound);
    }

    static DoublePredicate GT(double bound) {
        return new PredicateGT(bound);
    }

    private static final class PredicateLT extends ComparisonPredicate {
        PredicateLT(double bound) {
            super(bound);
        }

        PredicateLT(double bound, double tolerance) {
            super(bound, tolerance);
        }

        PredicateLT(double bound, DoubleComparator comparator) {
            super(bound, comparator);
        }

        @Override
        public boolean test(double value) {
            return comparator.LT(value, bound);
        }
    }

    private static final class PredicateLE extends ComparisonPredicate {
        PredicateLE(double bound) {
            super(bound);
        }

        PredicateLE(double bound, double tolerance) {
            super(bound, tolerance);
        }

        PredicateLE(double bound, DoubleComparator comparator) {
            super(bound, comparator);
        }

        @Override
        public boolean test(double value) {
            return comparator.LE(value, bound);
        }
    }

    private static final class PredicateGE extends ComparisonPredicate {
        PredicateGE(double bound) {
            super(bound);
        }

        PredicateGE(double bound, double tolerance) {
            super(bound, tolerance);
        }

        PredicateGE(double bound, DoubleComparator comparator) {
            super(bound, comparator);
        }

        @Override
        public boolean test(double value) {
            return comparator.GE(value, bound);
        }
    }

    private static final class PredicateGT extends ComparisonPredicate {
        PredicateGT(double bound) {
            super(bound);
        }

        PredicateGT(double bound, double tolerance) {
            super(bound, tolerance);
        }

        PredicateGT(double bound, DoubleComparator comparator) {
            super(bound, comparator);
        }

        @Override
        public boolean test(double value) {
            return comparator.GT(value, bound);
        }
    }
}

