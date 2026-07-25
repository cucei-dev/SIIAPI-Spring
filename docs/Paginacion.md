## Paginacion

Los endpoints de listado soportan paginacion via query params:

| Parametro | Tipo | Default | Descripcion |
|-----------|------|---------|-------------|
| `skip` | int | 0 | Elementos a saltar |
| `limit` | int | 100 | Maximo de elementos (max: 100) |

**Respuesta paginada:**

```json
{
  "total": 250,
  "results": [...]
}
```
