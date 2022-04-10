

function editCart(shoppingItemId, buyCount) {
    window.location.href
        ='shoppingItem.do?operate=editShoppingItem&shoppingItemId='+shoppingItemId+'&buyCount='+buyCount;
}




window.onload=function() {
    var vue = new Vue({
        el:"#cart_div",
        data:{},
        methods:{
            getCart:function() {
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'cartInfo'
                    }
                })
                    .then(function(value){
                        console.log(value.data);
                    })
                    .catch(function(reason){});
            }
        },
        mounted:function() {
                this.getCart();
            }
    });
}


