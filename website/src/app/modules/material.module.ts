import { NgModule } from '@angular/core';

import {
  MatToolbarModule, MatButtonModule, MatSidenavModule, MatListModule, MatGridListModule,
  MatCardModule, MatMenuModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatNativeDateModule
} from '@angular/material';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule, MatIconRegistry } from '@angular/material';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatButtonToggleModule} from '@angular/material/button-toggle';


@NgModule({
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDialogModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatButtonToggleModule
  ],
  exports: [
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDialogModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatButtonToggleModule
  ],
  providers: [
    MatIconRegistry
  ]
})
export class MaterialModule { }
