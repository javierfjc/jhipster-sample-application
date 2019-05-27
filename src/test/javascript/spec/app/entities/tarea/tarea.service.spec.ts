/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TareaService } from 'app/entities/tarea/tarea.service';
import { ITarea, Tarea, TareasEstado } from 'app/shared/model/tarea.model';

describe('Service Tests', () => {
  describe('Tarea Service', () => {
    let injector: TestBed;
    let service: TareaService;
    let httpMock: HttpTestingController;
    let elemDefault: ITarea;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TareaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Tarea(0, 'AAAAAAA', TareasEstado.ASIGNADO, currentDate, currentDate, currentDate, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaCreado: currentDate.format(DATE_TIME_FORMAT),
            fechaPrevistoInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFinal: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Tarea', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCreado: currentDate.format(DATE_TIME_FORMAT),
            fechaPrevistoInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFinal: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreado: currentDate,
            fechaPrevistoInicio: currentDate,
            fechaInicio: currentDate,
            fechaFinal: currentDate
          },
          returnedFromService
        );
        service
          .create(new Tarea(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Tarea', async () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            estado: 'BBBBBB',
            fechaCreado: currentDate.format(DATE_TIME_FORMAT),
            fechaPrevistoInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFinal: currentDate.format(DATE_TIME_FORMAT),
            horasPrevisto: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreado: currentDate,
            fechaPrevistoInicio: currentDate,
            fechaInicio: currentDate,
            fechaFinal: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Tarea', async () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            estado: 'BBBBBB',
            fechaCreado: currentDate.format(DATE_TIME_FORMAT),
            fechaPrevistoInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFinal: currentDate.format(DATE_TIME_FORMAT),
            horasPrevisto: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreado: currentDate,
            fechaPrevistoInicio: currentDate,
            fechaInicio: currentDate,
            fechaFinal: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Tarea', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
