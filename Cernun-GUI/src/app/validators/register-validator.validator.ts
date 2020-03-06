import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';

export class RegisterValidator {

  public static mustMatch(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.get(`passwordFc`).value !== control.get(`passwordVerifFc`).value ? {must_match: control} : null;
    };
  }

  public static mustDiff(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.get(`accountNameFc`).value === control.get(`userNameFc`).value ? {must_diff: control} : null;
    };
  }
}


