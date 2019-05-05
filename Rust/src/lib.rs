#[macro_use]
extern crate lazy_static;
extern crate num;
#[cfg(test)]
#[macro_use]
extern crate quickcheck;

use std::collections::HashMap;
use std::iter::{Iterator, IntoIterator};
use std::sync::Mutex;

use num::{BigUint, Zero, One};
use num::bigint::ToBigUint;
use num::iter::{range, range_inclusive};
use num::traits::ToPrimitive;
use core::borrow::BorrowMut;

fn iterative(n: &BigUint) -> BigUint {
    let mut result = BigUint::zero();
    let mut next = BigUint::one();
    let mut i = BigUint::zero();
    // Can't use for loop as BigUint doesn't implement needed traits.
    while i < *n {
        let temporary = next.clone();
        next = next + result;
        result = temporary;
        i = i + BigUint::one();
    }
    result
}

fn naive_recursive(n: &BigUint) -> BigUint {
    if *n == BigUint::zero() || *n == BigUint::one() { n.clone() }
    else {
        naive_recursive(&(n.clone() - BigUint::one())) + naive_recursive(&(n.clone() - 2.to_biguint().unwrap()))
    }
}

lazy_static! {
static ref MEMO: Mutex<HashMap<BigUint, BigUint>> = {
        let mut m = HashMap::new();
        m.insert(BigUint::zero(), BigUint::zero());
        m.insert(BigUint::one(), BigUint::one());
        Mutex::new(m)
    };
}

fn memoised_recursive(n: &BigUint) -> BigUint {
    if let mut memo = MEMO.lock().unwrap() {
        fn m_r(nn: &BigUint, mm: &mut HashMap<BigUint, BigUint>) -> BigUint {
            match mm.get(nn) {
                Some(item) => item.clone(),
                None => {
                    let result = m_r(&(nn.clone() - BigUint::one()), mm) + m_r(&(nn.clone() - 2.to_biguint().unwrap()), mm);
                    mm.insert(nn.clone(), result.clone());
                    result
                },
            }
        }
        m_r(n, &mut memo)
    } else {
        panic!("Could not get the memo.")
    }
}

fn foldive(n: &BigUint) -> BigUint {
    range(BigUint::zero(), n.clone()).fold((BigUint::zero(), BigUint::one()), |x, _| (x.0.clone() + x.1, x.0)).0
}

#[cfg(test)]
mod tests {
    use num::{BigUint, Zero, One};
    use num::bigint::ToBigUint;

    use super::{iterative, naive_recursive, memoised_recursive, foldive};

    // Cannot use BigInt as parameters for quickcheck test functions.

    quickcheck!{
        fn test_iterative(n: u64) -> bool {
            iterative(&(n + 2).to_biguint().unwrap()) == iterative(&(n + 1).to_biguint().unwrap()) + iterative(&n.to_biguint().unwrap())
        }
    }

    quickcheck!{
        // NB We severely restrict the values of n for this version to avoid long run times.
        fn test_naive_recursive(n: u8) -> bool {
            if n < 20 {
                naive_recursive(&(n + 2).to_biguint().unwrap()) == naive_recursive(&(n + 1).to_biguint().unwrap()) + naive_recursive(&n.to_biguint().unwrap())
            } else {
                true
            }
        }
    }

    quickcheck!{
        fn test_memoised_recursive(n: u64) -> bool {
            memoised_recursive(&(n + 2).to_biguint().unwrap()) == memoised_recursive(&(n + 1).to_biguint().unwrap()) + memoised_recursive(&n.to_biguint().unwrap())
        }
    }

    quickcheck!{
        fn test_foldive(n: u64) -> bool {
            foldive(&(n + 2).to_biguint().unwrap()) == foldive(&(n + 1).to_biguint().unwrap()) + foldive(&n.to_biguint().unwrap())
        }
    }

}
