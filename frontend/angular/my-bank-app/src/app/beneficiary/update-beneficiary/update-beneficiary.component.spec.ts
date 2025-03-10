import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateBeneficiaryComponent } from './update-beneficiary.component';

describe('UpdateBeneficiaryComponent', () => {
  let component: UpdateBeneficiaryComponent;
  let fixture: ComponentFixture<UpdateBeneficiaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateBeneficiaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateBeneficiaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
