## Clases

### `POST /api/v1/clases`

**Body:**

```json
{
  "sesion": "1",
  "horaInicio": "08:00:00",
  "horaFin": "09:30:00",
  "dia": 1,
  "seccionId": 1,
  "aulaId": 1
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `sesion` | string | No | Numero de sesion |
| `horaInicio` | time | No | Hora de inicio |
| `horaFin` | time | No | Hora de fin |
| `dia` | int | No | Dia (1=Lun, 2=Mar, ..., 6=Sab) |
| `seccionId` | long | Si | ID de la seccion |
| `aulaId` | long | No | ID del aula |

**Respuesta 201:**

```json
{
  "sesion": "1",
  "horaInicio": "08:00:00",
  "horaFin": "09:30:00",
  "dia": 1,
  "seccionId": 1,
  "aulaId": 1,
  "id": 1,
  "seccion": null,
  "aula": {
    "name": "101",
    "edificioId": 1,
    "id": 1
  }
}
```

### `GET /api/v1/clases`

Lista todas las clases.

**Query params:** `seccionId`, `aulaId`, `skip`, `limit`

### `GET /api/v1/clases/{id}`

Obtiene una clase por ID.

### `PUT /api/v1/clases/{id}`

Actualiza una clase.

### `PATCH /api/v1/clases/{id}`

Actualiza parcialmente una clase.

### `DELETE /api/v1/clases/{id}`

Elimina una clase. **Respuesta 204.**
