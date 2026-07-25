## Sync

El endpoint de sincronizacion recibe el estado completo de SIIAU y calcula el diff automaticamente para crear, actualizar o eliminar solo lo que cambio.

### `POST /api/v2/sync?calendarioId={id}&centroId={id}`

**Query params:**

| Parametro | Tipo | Requerido | Descripcion |
|-----------|------|-----------|-------------|
| `calendarioId` | long | Si | ID del calendario |
| `centroId` | long | Si | ID del centro |

**Headers:**

```
X-API-Key: tu-api-key
Content-Type: application/json
```

**Body:**

```json
{
  "secciones": [
    {
      "nrc": "12345",
      "clave": "TC1024",
      "materia": "Programacion Orientada a Objetos",
      "sec": "001",
      "cr": 8,
      "cup": 40,
      "dis": 15,
      "est": "EST",
      "profesor": "Juan Perez Garcia",
      "periodo": "12/08/25 - 19/12/25",
      "clases": [
        {
          "sesion": "1",
          "horaInicio": "0800",
          "horaFin": "0930",
          "dia": 1,
          "edificio": "C-1",
          "aula": "101"
        },
        {
          "sesion": "1",
          "horaInicio": "0800",
          "horaFin": "0930",
          "dia": 3,
          "edificio": "C-1",
          "aula": "101"
        },
        {
          "sesion": "2",
          "horaInicio": "1000",
          "horaFin": "1130",
          "dia": 2,
          "edificio": "C-1",
          "aula": "102"
        }
      ]
    }
  ]
}
```

#### Campos de SyncSeccion

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `nrc` | string | Si | NRC de la seccion |
| `clave` | string | Si | Clave de la materia |
| `materia` | string | Si | Nombre de la materia |
| `sec` | string | Si | Numero de seccion |
| `cr` | int | Si | Creditos |
| `cup` | int | Si | Cupos totales |
| `dis` | int | Si | Cupos disponibles |
| `est` | string | No | Estado |
| `profesor` | string | No | Nombre del profesor |
| `periodo` | string | No | Periodo (formato: "dd/MM/yy - dd/MM/yy") |
| `clases` | list | No | Lista de clases/horarios |

#### Campos de SyncClase

| Campo | Tipo | Descripcion |
|-------|------|-------------|
| `sesion` | string | Numero de sesion |
| `horaInicio` | string | Hora inicio (formato: "HHmm" o "HH:mm") |
| `horaFin` | string | Hora fin (formato: "HHmm" o "HH:mm") |
| `dia` | int | Dia (1=Lun, 2=Mar, ..., 6=Sab) |
| `edificio` | string | Nombre del edificio |
| `aula` | string | Nombre del aula |

#### Respuesta

```json
{
  "seccionesCreadas": 5,
  "seccionesActualizadas": 120,
  "seccionesEliminadas": 3,
  "clasesCreadas": 45,
  "clasesActualizadas": 300,
  "clasesEliminadas": 15,
  "materiasCreadas": 2,
  "profesoresCreados": 1,
  "edificiosCreados": 0,
  "aulasCreadas": 0
}
```

#### Logica del diff

Para cada seccion (agrupada por NRC):

1. **No existe en DB** -> Se crea la seccion, materia, profesor y clases
2. **Ya existe** -> Se comparan campos. Si algo cambio, se actualiza. Si no, se preserva el ID
3. **Clases** -> Se usa una clave natural `(sesion, horaInicio, horaFin, dia)`:
   - Clase nueva -> Se crea
   - Clase existente sin cambios -> No se toca
   - Clase existente con cambios -> Se actualiza
   - Clase en DB pero no en request -> Se elimina
4. **Secciones en DB no estan en request** -> Se eliminan
