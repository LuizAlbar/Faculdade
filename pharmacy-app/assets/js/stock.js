const productName = document.querySelector('#productName');
class Product {
    constructor() {
        this.id = 1;
        this.arrayProducts = [];
        this.editID = null;
    }

    add() {
        let product = this.read();

        if (this.emptyValidation(product)) {

            if (this.editID == null) {
                this.toStock(product);
            } else {
                this.update(this.editID, product);
            }
            alert('Product added!');
        }

        this.listTable();
        this.reset();
    }
    emptyValidation(product) {

        let validation = '';

        if (product.nameProduct === '') {

            const name = document.querySelector("#productName").placeholder = "Please, enter the product name!";
            validation += 'A';
        }
        

        if (product.priceProduct === '') {

            const price = document.querySelector("#productPrice").placeholder = "Please, enter the product price!";
            validation += 'A';
         
        }
        if (product.quantityProduct === '') {

            const quantity = document.querySelector("#productQuantity").placeholder = "Please, enter the product quantity!";
            validation += 'A';
          
        }
        if (product.userProduct === '') {

            const name = document.querySelector("#productAdder").placeholder = "Please, enter the product adder!";
            validation += 'A';
           
        }

       if(validation != ''){
        return false;

       }


       document.querySelector("#productName").placeholder = "";
       document.querySelector("#productPrice").placeholder = "";
       document.querySelector("#productQuantity").placeholder = "";
       document.querySelector("#productAdder").placeholder = "";

       return true;
       


    }

    listTable() {
        let tbody = document.getElementById('tbody');
        tbody.innerText = '';

        for (let i = 0; i < this.arrayProducts.length; i++) {
            let tr = tbody.insertRow();
            let td_id = tr.insertCell();
            let td_name = tr.insertCell();
            let td_price = tr.insertCell();
            let td_quantity = tr.insertCell();
            let td_cd = tr.insertCell();
            let td_user = tr.insertCell();
            let td_action = tr.insertCell();

            td_id.innerText ='#' + this.arrayProducts[i].id;
            td_name.innerText = this.arrayProducts[i].nameProduct;
            td_price.innerText = 'R$' + this.arrayProducts[i].priceProduct;
            td_quantity.innerText = this.arrayProducts[i].quantityProduct;
            td_cd.innerText = this.arrayProducts[i].cdProduct;
            td_user.innerText = this.arrayProducts[i].userProduct;

            let imgEdit = document.createElement('img');
            imgEdit.src = './assets/images/stock/editIcon.png';
            imgEdit.setAttribute("onclick", "product.edit(" + JSON.stringify(this.arrayProducts[i]) + ")");

            let imgDelete = document.createElement('img');
            imgDelete.src = './assets/images/stock/deleteIcon.png';
            imgDelete.setAttribute("onclick", "product.delete(" + this.arrayProducts[i].id + ")");

            td_action.appendChild(imgEdit);
            td_action.appendChild(imgDelete);
        }
    }

    toStock(product) {
        this.arrayProducts.push(product);
        this.id++;
    }

    reset() {
        let inputs = document.getElementsByTagName('input');
        for (let input of inputs) {
            input.value = '';
        }
        document.getElementById('btnAdd').value = 'Add';
        this.editID = null;
    }

    edit(data) {
        this.editID = data.id;
        document.getElementById('productName').value = data.nameProduct;
        document.getElementById('productPrice').value = data.priceProduct;
        document.getElementById('productQuantity').value = data.quantityProduct;
        document.getElementById('productAdder').value = data.userProduct;
        document.getElementById('btnAdd').innerText = "Update";
    }

    update(id, product) {
        for (let i = 0; i < this.arrayProducts.length; i++) {
            if (this.arrayProducts[i].id == id) {
                this.arrayProducts[i].nameProduct = product.nameProduct;
                this.arrayProducts[i].priceProduct = product.priceProduct;
                this.arrayProducts[i].quantityProduct = product.quantityProduct;
                this.arrayProducts[i].cdProduct = product.cdProduct;
                this.arrayProducts[i].userProduct = product.userProduct;
            }
        }
        this.editID = null;
        document.getElementById('btnAdd').innerText = "Add";
        this.listTable();
    }

    read() {
        let product = {};
        product.id = this.id;
        product.nameProduct = document.getElementById('productName').value;
        product.priceProduct = document.getElementById('productPrice').value;
        product.quantityProduct = document.getElementById('productQuantity').value;
        product.userProduct = document.getElementById('productAdder').value;


        const now = new Date();
        product.cdProduct = now.toLocaleString("en-US", { timeZone: "America/Sao_Paulo" });

        return product;
    }

   

    delete(id) {
        if (confirm('Do you really want to delete the product from ID ' + id + '?')) {
            let tbody = document.getElementById('tbody');
            for (let i = 0; i < this.arrayProducts.length; i++) {
                if (this.arrayProducts[i].id == id) {
                    this.arrayProducts.splice(i, 1);
                    tbody.deleteRow(i);
                }
            }
        }
    }
}

var product = new Product();
