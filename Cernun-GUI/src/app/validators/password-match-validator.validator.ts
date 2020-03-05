import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

export class PasswordMatchValidator {

  public static mustMatch(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.get(`passwordFc`).value !== control.get(`passwordVerifFc`).value ? {must_match: control} : null;
    };
  }
}
