import { FormGroup, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';


/**
* Update all controls of the provided form group with the given data.
*/
export function updateForm(group: FormGroup, data: any) {
  for (const field in group.controls) {
    const control = group.get(field)!;
    let value = data[field] === undefined ? null : data[field];
    control.setValue(value);
  }
}

export const validDouble: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const valid = control.value === null || /^(-?)[0-9]*(\.[0-9]+)?$/.test(control.value);
  return valid ? null : { validDouble: { value: control.value } };
};
