export class Page<T> {
  totalPages: number;
  totalElements: number;
  pageable: Pageable;
  first: boolean;
  sort: Sort;
  numberOfElements: number;
  last: boolean;
  size: number;
  content: T[];
  number: number;
  empty: boolean;

  constructor() {
    this.totalElements = 0;
    this.totalPages = 0;
    this.first = true;
    this.numberOfElements = 0;
    this.last = true;
    this.size = 0;
    this.content = [];
    this.empty = true;
  }
}

interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
  paged: boolean;
  unpaged: boolean;
  offset: number;
}

interface Sort {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}
